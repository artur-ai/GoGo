let currentPage = 0;
const pageSize = 6;

document.addEventListener('DOMContentLoaded', () => {
    const prevBtn = document.getElementById('prev-btn');
    const nextBtn = document.getElementById('next-btn');

    if (prevBtn) prevBtn.onclick = () => changePage(-1);
    if (nextBtn) nextBtn.onclick = () => changePage(1);

    const burger = document.getElementById('burger');
    const navLinks = document.querySelector('.nav-links');
    if (burger && navLinks) {
        burger.onclick = () => navLinks.classList.toggle('active');
    }

    loadCars(currentPage);
});

async function loadCars(page) {
    const container = document.getElementById('car-list-container');
    const pageInfo = document.getElementById('page-info');
    const prevBtn = document.getElementById('prev-btn');
    const nextBtn = document.getElementById('next-btn');

    if (!container) return;

    container.innerHTML = '<h2 style="text-align: center; grid-column: 1/-1;">Завантаження...</h2>';

    const apiUrl = `/api/v1/cars?page=${page}&size=${pageSize}`;

    try {
        const response = await fetch(apiUrl);

        if (!response.ok) {
            throw new Error(`HTTP Error! Status: ${response.status}`);
        }

        const data = await response.json();
        const cars = data.content;

        container.innerHTML = '';

        if (cars && cars.length > 0) {
            cars.forEach(car => {
                container.innerHTML += renderCarCard(car);
            });
        } else {
            container.innerHTML = '<h2 style="grid-column: 1/-1; text-align: center;">Автомобілів не знайдено</h2>';
        }

        if (pageInfo) {
            pageInfo.innerText = `Сторінка ${data.pageNumber + 1} з ${data.totalPages || 1}`;
        }

        if (prevBtn) prevBtn.disabled = (data.pageNumber === 0);
        if (nextBtn) nextBtn.disabled = (data.isLast || data.pageNumber >= data.totalPages - 1);

    } catch (error) {
        console.error('Помилка завантаження:', error);
        container.innerHTML = `<h2 style="color: red; grid-column: 1/-1; text-align: center;">Помилка: Не вдалося завантажити автомобілі. ${error.message}</h2>`;
    }
}

function renderCarCard(car) {
    const bookingUrl = `Cars-booking/${car.model}.html`;

    return `
        <div class="car-card">
            <img src="${car.imageUrl}" alt="${car.brand} ${car.model}" class="car-img">
            <div class="car-info">
                <h2>${car.brand} ${car.model} ${car.year}</h2>
                <p>${car.engine} • ${car.fuelType}</p>
                <ul>
                    <li>Хвилина: ${car.pricePerMinute} грн</li>
                    <li>Доба: ${car.pricePerDay} грн</li>
                    <li>Страхування: ${car.insurancePrice} грн(хв)</li>
                    <a href="${bookingUrl}" class="select-button">Вибрати це авто</a>
                </ul>
            </div>
        </div>
    `;
}

function changePage(step) {
    currentPage += step;
    if (currentPage < 0) currentPage = 0;
    loadCars(currentPage);
    window.scrollTo({ top: 0, behavior: 'smooth' });
}