let widgets = null;
let paymentMethodsWidget = null;

async function initTossPayments(pnum) {
  const payBtn = document.getElementById("payment-button");
  if (!payBtn) return;

  // 이미 렌더된 상태면 중복 렌더 방지
  if (widgets) return;

  const amount = Number(payBtn.dataset.price);
  const dealType = payBtn.dataset.deal.type;

  const clientKey = "test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm"; //결제 위젯 연동 테스트 키
  const customerKey = "ANONYMOUS"; // 또는 로그인 유저 아이디 넣어주기

  const tossPayments = TossPayments(clientKey);
  widgets = tossPayments.widgets({ customerKey });

  await widgets.setAmount({ currency: "KRW", value: amount });

  //결제수단/약관 렌더
  await widgets.renderPaymentMethods({
    selector: "#payment-method",
    variantKey: "DEFAULT"
  });

  await widgets.renderAgreement({
    selector: "#agreement",
    variantKey: "AGREEMENT"
  });


  // 3) 결제 버튼 클릭 → 결제 요청
  payBtn.addEventListener("click", async () => {
    try {
		//orderId 생성 및 주문 레코드 생성
		const response = await fetch("/order/prepare", {
	        method: "POST",
	        headers: { "Content-Type": "application/json" },
	        body: JSON.stringify({pnum, dealType})
		});

		if (!response.ok) throw new Error("prepare failed");
	
		//orderId랑 amount는 서버에서 가져와서 설정해줌
		const { orderName, orderId, amount } = await response.json();
		
		sessionStorage.setItem("returnUrl", location.href);
	  
	  	//결제 금액 맞춰주기
	  	await widgets.setAmount({ currency: "KRW", value: amount });
	  
      	await widgets.requestPayment({
        	orderId,
        	orderName,
        	successUrl: `${location.origin}/order/toss`,
        	failUrl: `${location.origin}/order/fail`,
      	});
    } catch (e) {
      	// 자주 나는 에러들: 약관 미동의/결제수단 미선택 등
      	console.error(e);
      	alert(e?.message || "결제 요청 중 오류가 발생했습니다.");
   	}
  });
}
