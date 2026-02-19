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
		
		case "my-page" : {
			location.href="/myPage/myPageFrm";
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

/* toss api 부르기 */
function openPaymentForm(pnum, type) { 
	loadDrawerContent(`/buy/payment/${pnum}?type=${type}`, () => { 
		initTossPayments();
	});
}