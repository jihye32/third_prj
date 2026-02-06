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