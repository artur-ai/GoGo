document.addEventListener('DOMContentLoaded', () => {
    const burger = document.getElementById('burger');
    const nav = document.querySelector('header nav');

    if (burger && nav) {
        burger.addEventListener('click', () => {
            nav.classList.toggle('active');
        });
    }

    function renderCarCard(car) {
        return `
            <div class="car-card">
                <img src="${car.imageUrl}" alt="${car.brand} ${car.model}">
                <h3>${car.brand} ${car.model}</h3>
                <p>${car.year}</p>
                <p>${car.engine}</p>
                <p>${car.fuelType}</p>
            </div>
        `;
    }

async function loadFeaturedCars() {
    const container = document.querySelector('.car-list');
    if (!container) return;

    try {

        const response = await fetch('/api/v1/cars/random');

        if (!response.ok) throw new Error(`Status: ${response.status}`);

        const cars = await response.json();

        container.innerHTML = cars.length > 0
            ? cars.map(car => renderCarCard(car)).join('')
            : '<p>Машини не знайдено</p>';

    } catch (error) {
        console.error('Error:', error);
        container.innerHTML = '<p style="color: red;">Помилка завантаження</p>';
    }
}


    loadFeaturedCars();
});