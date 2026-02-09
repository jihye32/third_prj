/**
 * 채팅
 */
function getChatRoot() {
  return document.getElementById("drawerContent"); // ✅ 항상 drawer 기준
}

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

// 전송 버튼 클릭
document.addEventListener("click", (e) => {
  const btn = e.target.closest("#send-chat");
  if (!btn) return;
  if (btn.disabled) return; // disabled면 무시

  const root = getChatRoot();
  if (!root) return;

  sendChatMessage(root);
});

// Enter 전송 / Shift+Enter 줄바꿈
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

async function sendChatMessage(root) {
  const input = root.querySelector("#chat-input");
  if (!input) return;

  const content = input.value.trim();
  if (!content) return;

  const productNum = PageContext.pnum;
  let roomNum = root.querySelector("#room-num")?.value || null;

  try {
    const res = await fetch("/chat/send", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ roomNum, productNum, content }),
    });
    if (!res.ok) throw new Error("send failed");

    const data = await res.json();
    // 서버가 roomId를 생성해줬으면 hidden에 반영
    if (!roomNum && data.roomNum) {
      root.querySelector("#room-num").value = data.roomNum;
      roomNum = data.roomNum;
      // (선택) URL을 room 기반으로 바꾸고 싶으면:
      // history.replaceState(null, "", `/chat/room/${roomNum}`);
    }

    // 화면에 append
    appendMyMessage(root, data);

    // 입력 초기화
    input.value = "";
    root.querySelector("#chat-input") && (root.querySelector("#chat-input").textContent = "0");

  } catch (e) {
    alert("메시지 전송에 실패했습니다.");
    console.error(e);
  }
}

function appendMyMessage(root, { content, timeText, readText }) {
  const box = root.querySelector("#chat-messages");
  const tpl = root.querySelector("#my-message");
  if (!box || !tpl) return;

  const node = tpl.content.cloneNode(true);
  node.querySelector(".msg-text").textContent = content;
  node.querySelector(".msg-time").textContent = timeText || "";
  node.querySelector(".msg-read").textContent = readText || "";

  box.appendChild(node);
  box.scrollTop = box.scrollHeight;
}


