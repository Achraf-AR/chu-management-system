function toggleDrawer() {
    const drawer = document.getElementById('drawer');
    drawer.classList.toggle('open');
}

// Close drawer when clicking outside
document.addEventListener('click', function(event) {
    const drawer = document.getElementById('drawer');
    const menuBtn = document.querySelector('.menu-btn');
    
    if (!drawer.contains(event.target) && !menuBtn.contains(event.target) && drawer.classList.contains('open')) {
        drawer.classList.remove('open');
    }
});