/* 기준이 되는 틀 생성해줌 */
const loadDrawerContent = (url, callback) => {
	fetch(url)
		.then(res => res.text())
		.then(html => {
		    const drawer = document.getElementById('drawerContent');
		    drawer.innerHTML = html;
		    drawer.scrollTop = 0;
		    if (callback) callback();
		})
		.catch(err => console.error('Drawer Load Error:', err));
};

function closeDrawer() {
 	// 서랍 닫기
 	document.getElementById('drawer').classList.add('translate-x-full');

 	// 배경 숨기기
 	const overlay = document.getElementById('overlay');
 	overlay.classList.add('opacity-0');
 	setTimeout(() => overlay.classList.add('hidden'), 300); // 애니메이션 시간만큼 대기

 	// 뒷배경 스크롤 허용
 	document.body.style.overflow = '';
} 