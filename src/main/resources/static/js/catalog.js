document.addEventListener('DOMContentLoaded', () => {
    const burger = document.getElementById('burger');
    const navLinks = document.querySelector('.nav-links');
    const container = document.getElementById('car-list-container');
    const tagFiltersContainer = document.getElementById('tag-filters-container');
    const applyFiltersButton = document.getElementById('apply-filters-button');

    if (burger && navLinks) {
        burger.addEventListener('click', () => {
            navLinks.classList.toggle('active');
        });
    }

    function renderCarCard(car) {
        // Використовуємо тимчасову заглушку, оскільки логіка bookingUrl є складною
        const bookingUrl = `delivery.html?carId=${car.id}`;

        // Додаємо відображення тегів до картки
        const tagsHtml = car.tags && car.tags.length > 0
            ? car.tags.map(tag => `<span class="tag-badge">${tag.name}</span>`).join('')
            : '';

        return `
            <div class="car-card">
              <img src="${car.imageUrl}" alt="${car.brand} ${car.model}" class="car-img">
              <div class="car-info">
                <h2>${car.brand} ${car.model} ${car.year}</h2>
                <p>${car.engine} • ${car.fuelType}</p>
                <div class="car-tags-list">${tagsHtml}</div>
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

    // --- ФУНКЦІЯ 4: Завантаження та відображення автомобілів (ОНОВЛЕНО) ---
    // Приймає URL для фільтрації
    function loadCars(url = '/api/cars') {
        if (!container) return;

        container.innerHTML = '<h2 style="text-align: center;">Завантаження каталогу...</h2>';

        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error('HTTP error! Status: ' + response.status);
                }
                return response.json();
            })
            .then(cars => {
                container.innerHTML = '';
                if (cars.length === 0) {
                    container.innerHTML = `<h2 style="text-align: center;">Автомобілів за вибраними фільтрами не знайдено.</h2>`;
                    return;
                }

                cars.forEach(car => {
                    container.innerHTML += renderCarCard(car);
                });
            })
            .catch(error => {
                console.error('Помилка завантаження даних:', error);
                container.innerHTML = `<h2 style="color: red; text-align: center;">Помилка: Не вдалося завантажити автомобілі.</h2>`;
            });
    }

    // --- ФУНКЦІЯ 5: Обробка фільтрації ---
    function filterCars() {
        // Збираємо ID усіх вибраних чекбоксів
        const selectedTagIds = Array.from(document.querySelectorAll('.tag-filter-checkbox:checked'))
                                    .map(checkbox => checkbox.value);

        let url = '/api/cars';

        if (selectedTagIds.length > 0) {
            // Формуємо рядок параметрів запиту: ?tagIds=1,5,8
            const tagQuery = selectedTagIds.join(',');
            url = `/api/cars?tagIds=${tagQuery}`;
        }

        loadCars(url);
    }

    // --- ФУНКЦІЯ 6: Завантаження та відображення тегів з API ---
    async function loadAndDisplayTags() {
        if (!tagFiltersContainer) return;
        tagFiltersContainer.innerHTML = '<p>Завантаження фільтрів...</p>';

        try {
            const response = await fetch('/api/tags');
            if (!response.ok) throw new Error('Failed to fetch tags');

            const tags = await response.json();

            tagFiltersContainer.innerHTML = ''; // Очищаємо контейнер

            tags.forEach(tag => {
                const tagElement = document.createElement('div');
                tagElement.className = 'filter-item';
                tagElement.innerHTML = `
                    <input type="checkbox" id="tag-${tag.id}" value="${tag.id}" class="tag-filter-checkbox">
                    <label for="tag-${tag.id}">${tag.name}</label>
                `;
                tagFiltersContainer.appendChild(tagElement);
            });

            // Додаємо слухача подій до кнопки "Застосувати"
            if (applyFiltersButton) {
                applyFiltersButton.addEventListener('click', filterCars);
            }

        } catch (error) {
            console.error('Error loading tags:', error);
            tagFiltersContainer.innerHTML = '<p>Не вдалося завантажити фільтри.</p>';
        }
    }


    // --- Запуск при завантаженні сторінки ---
    loadAndDisplayTags(); // Спочатку завантажуємо фільтри
    loadCars();           // Завантажуємо всі авто (перший раз)
});