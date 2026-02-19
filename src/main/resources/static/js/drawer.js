/* 기준이 되는 틀 생성해줌 */
const loadDrawerContent = (url, callback) => {
	fetch(url)
		.then(res => {
	      if (res.status === 401) {
	        window.top.location.href = "/user/login/loginFrm"; // 부모로 이동
	        return null;
	      }
	      return res.text();
	    })
		.then(html => {
		    const drawer = document.getElementById('drawerContent');
		    drawer.innerHTML = html;
		    drawer.scrollTop = 0;
		    if (callback) callback();
		})
		.catch(err => console.error('Drawer Load Error:', err));
		console.log("fetch is", fetch);
		const res = fetch(url);
		console.log("res is", res);
};

function openDrawer(title) {
	const titleElement = document.getElementById('drawerTitle');
	if (titleElement && title) {
    	titleElement.innerText = title;
	}

	// 서랍 열기
	document.getElementById('drawer').classList.remove('translate-x-full');

	// 배경 보이기
	const overlay = document.getElementById('overlay');
	overlay.classList.remove('hidden');
	setTimeout(() => overlay.classList.remove('opacity-0'), 10); // 부드러운 효과를 위해

	// 뒷배경 스크롤 방지
	document.body.style.overflow = 'hidden';
}

function closeDrawer() {
	const drawer = document.getElementById('drawer');
	const drawerContent = document.getElementById('drawerContent');

	if (drawerContent?.dataset?.mode === "chat") {
    	if (typeof window.resetSubscript === "function") {
      		window.resetSubscript();
    	}
  	}
  	drawerContent.dataset.mode = "";
	
 	// 서랍 닫기
 	drawer.classList.add('translate-x-full');

 	// 배경 숨기기
 	const overlay = document.getElementById('overlay');
 	overlay.classList.add('opacity-0');
 	setTimeout(() => overlay.classList.add('hidden'), 300); // 애니메이션 시간만큼 대기

 	// 뒷배경 스크롤 허용
 	document.body.style.overflow = '';
	
} 