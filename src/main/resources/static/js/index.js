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

        if (!container) {
            console.warn('Container .car-list not found');
            return;
        }

        container.innerHTML = '<p style="text-align: center; grid-column: 1/-1;">Завантаження...</p>';

        try {
            const response = await fetch('/api/cars/random?count=3');

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const cars = await response.json();
            console.log('Отримано випадкові машини:', cars);

            if (!cars || cars.length === 0) {
                container.innerHTML = '<p style="text-align: center; grid-column: 1/-1;">Машини не знайдено</p>';
                return;
            }

            const cardsHtml = cars.map(car => renderCarCard(car)).join('');
            container.innerHTML = cardsHtml;

        } catch (error) {
            console.error('Помилка завантаження випадкових машин:', error);
            container.innerHTML = '<p style="text-align: center; color: red; grid-column: 1/-1;">Не вдалося завантажити автомобілі</p>';
        }
    }
    loadFeaturedCars();
});