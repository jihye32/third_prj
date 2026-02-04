document.addEventListener("click", (e) => {
  const actionBtn = e.target.closest("[data-action]");
  if (actionBtn){
	switch (actionBtn.dataset.action) {
  	    case "select-deal-type": {//선택한 거래 방법을 저장
		   	// 1) UI 강조
		    document.querySelectorAll(".deal-option").forEach(opt => {
		      opt.classList.remove("border-gray-900");
		      opt.classList.add("border-gray-200");
		    });
		
		    actionBtn.classList.remove("border-gray-200");
		    actionBtn.classList.add("border-gray-900");
		
		    // 2) 선택값 저장 (DIRECT/DELIVERY)
		    setSelectedType(actionBtn.dataset.type);
		    return;
		}
		
  	    case "payment-submit": {//선택한 거래 방법을 저장
			const selectedType = getSelectedType();
		    if (!selectedType) {
		      alert("거래 방식을 선택해주세요.");
		      return;
		    }
			
		    const pnum = PageContext.pnum;
		    openPaymentForm(pnum, selectedType);
		    return;
		}
	}
  }
});


/* 선택한 타입을 가져와서 반환함 */
function setSelectedType(type) {
  const drawer = document.getElementById("drawerContent");
  drawer.dataset.selectedType = type; // "1" or "2"
}

function getSelectedType() {
  const drawer = document.getElementById("drawerContent");
  return drawer.dataset.selectedType || null;
}

function openPaymentForm(pnum, type) { 
	loadDrawerContent(`/buy/payment/${pnum}?type=${type}`, () => { 
		/*if (window.initTossPayments) {
            // 1. 로드된 버튼 요소를 찾습니다.
            const paymentBtn = document.getElementById("payment-button");
            
            if (paymentBtn) {
                // 2. data- 속성에 심어둔 값을 읽어옵니다.
                const title = paymentBtn.getAttribute("data-title");
                
                // 3. 읽어온 값을 인자로 넘기며 초기화 함수를 실행합니다.
                window.initTossPayments(pnum,title);
            }
        } */
	});
}