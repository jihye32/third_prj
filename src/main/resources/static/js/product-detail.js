/**
 * product_detail.html에서 사용되는 js
 */
/* 상세화면을 로드할 때 drawerUrl이 없으면 작동하지 않게 함 */
document.addEventListener("DOMContentLoaded", () => {
  const qs = new URLSearchParams(location.search);
  const drawerUrl = qs.get("drawerUrl");
  
  if (drawerUrl) {
    loadDrawerContent(drawerUrl, () => openDrawer('결제 결과'));

    qs.delete("drawerUrl");
	const cleanUrl = location.pathname + (qs.toString() ? "?" + qs.toString() : "");

    //주소창만 바꾸고, 새로고침/뒤로가기 히스토리는 유지
    history.replaceState(null, "", cleanUrl);
  }
  
  let currentIndex = 0;
      const slider = document.getElementById('image-slider');
      const currentText = document.getElementById('current-slide');
      
      if (slider && slider.children.length > 0) {
          const totalSlides = slider.children.length;

          window.updateSlider = function() {
              slider.style.transform = `translateX(-${currentIndex * 100}%)`;
              if (currentText) currentText.innerText = currentIndex + 1;
          };

          window.nextSlide = function() {
              currentIndex = (currentIndex < totalSlides - 1) ? currentIndex + 1 : 0;
              updateSlider();
          };

          window.prevSlide = function() {
              currentIndex = (currentIndex > 0) ? currentIndex - 1 : totalSlides - 1;
              updateSlider();
          };
      }
});



document.getElementById("heart-checkbox")?.addEventListener("change", async (e) => {
  const checkbox = e.target;
  const checked = checkbox.checked;
  const pnum = PageContext.pnum;

  try {
    const res = await fetch("/product/bookmark", {
      method: checked ? "POST" : "DELETE",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ pnum }),
    });

    if (!res.ok) throw new Error("bookmark failed");
  } catch (err) {
    checkbox.checked = !checked; // 원상복구
    alert("북마크 처리에 실패했습니다.");
    console.error(err);
  }
});



//버튼을 클릭하면 data-action의 값에 해당하는 함수 실행
document.addEventListener("click", (e) => {
  const actionBtn = e.target.closest("button[data-action]");
  const pnum = PageContext.pnum;
  
  if (actionBtn){
	  switch (actionBtn.dataset.action) {
	    case "bump-date": //끌올 시간 확인 및 변경
	      	bumpDate(pnum);
	      	return;
			
		case "open-status-list": {//상품 상태 변경 버튼 출력
			const container = actionBtn.closest(".status-container");
			const menu = container?.querySelector(".status-option");
			menu?.classList.toggle("hidden");
	    	return;
		}
		
		case "change-status": {
			const statusCode = actionBtn.dataset.option;
			updateStatus(pnum, statusCode);
			
			const container = actionBtn.closest(".status-container");
			const menu = container?.querySelector(".status-option");
			menu?.classList.add("hidden");
			return;
		}
			
		case "remove-product": //상품 삭제
	      	confirmRemove(pnum);
	      	return;
		
		case "send-product": //상품 발송
	      	sendProduct(pnum);
	      	return;
			
		case "buy-product": //상품 구매
	      	openBuyForm(pnum);
	      	return;
	  }
  }
  
  closeStatusDropdown();
});
function closeStatusDropdown(){
	document.querySelectorAll(".status-option").forEach((menu) => {
		menu.classList.add("hidden");
	});
}

//끌올 시간 확인 및 변경 함수
function bumpDate(pnum) {
	fetch('/product/modifyUpDate', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(pnum)
    }).then(res => res.json())
    .then(data => {
    	alert(data.msg); 
		if(data.flag){
			alert("현재 페이지 수정");
			$("#timeAgo").text("방금");
		}else{
			alert("현재 페이지 유지");
		}
    });
}

//상품 상태 변경 함수
function updateStatus(pnum, statusCode) {
    fetch('/product/status', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ pnum, sellStatusCode : statusCode })
	}).then(res => res.json())
    .then(data => {
    	alert(data.msg); 
		if(data.flag){
			alert("현재 페이지 수정");
		}else{
			alert("현재 페이지 유지");
		}
    });
}

//상품 삭제 함수
function confirmRemove(pnum) {
  if (!confirm("정말 삭제하시겠습니까?")) return;
  removeProduct(pnum);
}

function removeProduct(pnum) {
	fetch('/product/deleteProduct', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(pnum )
    }).then(res => res.json())
    .then(data => {
    	alert(data.msg); 
		if(data.flag){
			alert("마이페이지로 이동");
		}else{
			alert("현재 페이지 유지");
		}
    });
}

//발송완료 함수
function sendProduct(pnum){
	fetch('/product/send', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(pnum)
    }).then(res => res.json())
    .then(data => {
		alert(data.msg);
		if(data.flag){
			alert("현재 페이지에서 버튼 안보이게 하기");
		}else{
			alert("현재 페이지 유지");
		}
    });
}

function openBuyForm(pnum) { 
	loadDrawerContent(`/buy/${pnum}`, () => openDrawer('거래 방법 선택')); 
}
