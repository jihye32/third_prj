let widgets = null;
let paymentMethodsWidget = null;

async function initTossPayments() {
  const payBtn = document.getElementById("payment-button");
  if (!payBtn) return;


  // 이미 렌더된 상태면 중복 렌더 방지
  if (!widgets) {
      const clientKey = "test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm";
      const customerKey = "ANONYMOUS"; // 또는 로그인 유저 아이디 넣어주기
      widgets = TossPayments(clientKey).widgets({ customerKey });

	  const amount = Number(payBtn.dataset.price);
	  await widgets.setAmount({ currency: "KRW", value: amount });
	  
      // 결제수단/약관은 최초 1회 렌더
      await widgets.renderPaymentMethods({ selector: "#payment-method", variantKey: "DEFAULT" }); 
      await widgets.renderAgreement({ selector: "#agreement", variantKey: "AGREEMENT" }); 
    }


  // 3) 결제 버튼 클릭 → 결제 요청
  payBtn.addEventListener("click", async () => {
    try {
		//주문에 들어갈 정보들 초기화
		const dealType = payBtn.dataset.dealType;
		const pnum = PageContext.pnum;
		
		let address = null;
		
		if (dealType === "DELIVERY") {
			const name = document.querySelector('[name="deliveryUserName"]')?.value.trim();
			const tel = document.querySelector('[name="deliveryUserPhoneNo"]')?.value.trim();
			const addr = document.querySelector("#roadAddr")?.innerText.trim();
			const addrDetail = document.querySelector('[name="deliveryAddressDetail"]')?.value.trim();

		  if (!name || !tel || !addr) {
		    alert("배송 정보를 입력해주세요.");
		    return;
		  }

		  address = { name, tel, addr, addrDetail };
		}
		
		const data = {
			pnum, dealType, address
		}
		
		
		//orderId 생성 및 주문 레코드 생성
		const response = await fetch("/order/prepare", {
	        method: "POST",
	        headers: { "Content-Type": "application/json" },
	        body: JSON.stringify(data)
		});

		if (!response.ok) {
	      const msg = await response.text().catch(() => "");
	      throw new Error(msg || "prepare failed");
	    }
	
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
