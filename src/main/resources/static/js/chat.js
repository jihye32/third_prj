/**
 * 채팅
 */
function getChatRoot() {
  return document.getElementById("drawerContent"); // ✅ 항상 drawer 기준
}

/* 채팅 리스트 열기 */
function openChatDrawer() {
	fetch('/chat/list')
    .then(res => res.text())
    .then(html => {
       	// 서랍 내부의 콘텐츠 영역(ID: drawerContent)에 서버가 보낸 HTML 주입
       	document.getElementById('drawerContent').innerHTML = html;
       	openDrawer('채팅'); // 서랍 여는 공통 함수 실행
    });
}


/* 실시간 확인 및 열린 방 확인 */
let stompClient = null;
let currentRoom = null;
let sub = null;

/* 채팅 방 열기 */
function openChatForm(sellerId, store) { 
  const pnum = window.PageContext?.pnum ?? null;
  
 	let url = `/chat/${sellerId}`;

	if(pnum !== null){
		url+=`?pnum=${pnum}`;
	}

  loadDrawerContent(url, async () => {
    openDrawer(`${store}`);

    const root = document.querySelector("#chat-root");
    if (!root) return;

    const roomNum = root.querySelector("#room-num")?.value;
    if (roomNum) {
      await ensureConnectedAndSubscribe(roomNum, root);
    }
  });
}


/* 입력하는 글자 수 확인 */
document.addEventListener("input", (e) => {
  if (!(e.target instanceof HTMLTextAreaElement)) return;
  if (e.target.id !== "chat-input") return;

  const root = getChatRoot();
  if (!root) return;

  const count = root.querySelector("#chat-count");
  const sendBtn = root.querySelector("#send-chat");
  if (!count) return;

  if (e.target.value.length > 1000) {
    e.target.value = e.target.value.slice(0, 1000);
    alert("1000자까지만 가능합니다.");
  }

  count.textContent = String(e.target.value.length);

  //전송 버튼 활성/비활성
  if (sendBtn) {
    const hasText = e.target.value.trim().length > 0;
    sendBtn.disabled = !hasText;
    const svg = sendBtn.querySelector("svg");
    if (svg) svg.classList.toggle("fill-[#9CA3AF]", !hasText); // 비활성 색
    if (svg) svg.classList.toggle("fill-[#111827]", hasText);  // 활성 색(원하면)
  }
});

/* 전송 버튼 클릭 */
document.addEventListener("click", (e) => {
  const btn = e.target.closest("#send-chat");
  if (!btn) return;
  if (btn.disabled) return; // disabled면 무시

  const root = getChatRoot();
  if (!root) return;

  sendChatMessage(root);
});

/* Enter로 메시지 전송 / Shift+Enter 줄바꿈 */
document.addEventListener("keydown", (e) => {
  const ta = e.target;
  if (!(ta instanceof HTMLTextAreaElement)) return;
  if (ta.id !== "chat-input") return;

  if (e.key === "Enter" && !e.shiftKey) {
    e.preventDefault();

    const root = getChatRoot();
    if (!root) return;

    const sendBtn = root.querySelector("#send-chat");
    if (sendBtn?.disabled) return;

    sendChatMessage(root);
  }
});

/* 메시지 보내기 */
async function sendChatMessage(root) {
  const input = root.querySelector("#chat-input");
  if (!input) return;

  const content = input.value.trim();
  if (!content) return;

  const productNum = window.PageContext?.pnum ?? null;
  let roomNum = root.querySelector("#room-num")?.value || null;
  const otherId = root.querySelector("#other-id").value;
  try {
    const res = await fetch("/chat/send", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ roomNum, productNum, content, otherId }),
    });
    if (!res.ok) throw new Error("send failed");
    const data = await res.json();
    // 서버가 roomId를 생성해줬으면 hidden에 반영
    if (!roomNum && data.roomNum) {
      root.querySelector("#room-num").value = data.roomNum;
      roomNum = data.roomNum;
	  await ensureConnectedAndSubscribe(roomNum, root);
    }

    appendMyMessage(root, data);

    // 입력 초기화
    input.value = "";
	const counter = root.querySelector("#chat-count");
	if (counter) counter.textContent = "0";

  } catch (e) {
    alert("메시지 전송에 실패했습니다.");
    console.error(e);
  }
}

/* 소켓 연결 */
function ensureConnected() {
  return new Promise((resolve, reject) => {
    if (stompClient?.connected) return resolve();

    const socket = new SockJS("/ws");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, () => resolve(), (err) => reject(err));
  });
}

/* 소켓을 연결해주는 함수를 부르고 구도시켜 줌 */
async function ensureConnectedAndSubscribe(roomNum, root) {
  await ensureConnected();


  if (currentRoom === String(roomNum)) {
    return;
  }

  currentRoom = String(roomNum);

  const myId = root.querySelector("#user-id")?.value;

  sub = stompClient.subscribe(`/topic/room/${roomNum}`, (frame) => {
    
    const msg = JSON.parse(frame.body);

    if (myId && msg.writerId === myId) {
      return;
    }
	
	//상대방 메시지가 보여지는 거 + 읽음 처리하는 위치
    appendOtherMessage(root, msg);
	
	markRoomRead(roomNum);
	
  });

}

/* 실시간 읽음 처리 */
let readInFlight = new Map();

async function markRoomRead(roomNum) {
  const key = String(roomNum);

  if (readInFlight.has(key)) return readInFlight.get(key); //delete되기 전에 메시지가 온다면 같은 객체(p)로 처리함

  const p = (async () => {
    try {
      await fetch("/chat/read", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ roomNum: Number(roomNum) })
      });
    } finally {
      readInFlight.delete(key);
    }
  })();

  readInFlight.set(key, p);
  return p;
}




/* 메시지 폼 생성 */
function appendMyMessage(root, { content }) {
  const box = root.querySelector("#chat-messages");
  const tpl = root.querySelector("#my-message");
  if (!box || !tpl) return;

  const node = tpl.content.cloneNode(true);
  node.querySelector(".msg-text").textContent = content;
  node.querySelector(".msg-time").textContent =  formatChatTime()|| "";
  node.querySelector(".msg-read").textContent = "" || "";

  box.appendChild(node);
  box.scrollTop = box.scrollHeight;
}

function appendOtherMessage(root, { content }) {
  const box = root.querySelector("#chat-messages");

  const tpl = root.querySelector("#other-message");
  if (!box || !tpl) return;

  const node = tpl.content.cloneNode(true);
  node.querySelector(".msg-text").textContent = content;
  node.querySelector(".msg-time").textContent =  formatChatTime()|| "";

  box.appendChild(node);
  box.scrollTop = box.scrollHeight;
}

/*메시지 폼에 들어가는 시간 */ 
function formatChatTime(date = new Date()) {
  return date.toLocaleTimeString("ko-KR", {
    hour: "numeric",
    minute: "2-digit",
    hour12: true
  });
}

/* 방이 닫히면 초기화 시켜주기 */
function resetSubscript(){
	currentRoom = null;

	if (sub) {
	  sub.unsubscribe();
	  sub = null;
	}
}



