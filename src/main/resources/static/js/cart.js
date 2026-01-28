function getCart() {
    const match = document.cookie.match(/\btechzone_cart=([^;]*)/);
    if (!match || !match[1]) return [];
    try {
        const decoded = decodeURIComponent(match[1].trim());
        return JSON.parse(decoded);
    } catch (e) {
        console.error('Error parsing cart:', e);
        return [];
    }
}
