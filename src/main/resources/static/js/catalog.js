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
        const bookingUrl = `delivery.html?carId=${car.id}`;

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
                console.log('Отримано машини:', cars);

                if (cars.length === 0) {
                    container.innerHTML = `<h2 style="text-align: center;">Автомобілів за вибраними фільтрами не знайдено.</h2>`;
                    return;
                }

                const cardsHtml = cars.map(car => renderCarCard(car)).join('');
                container.innerHTML = cardsHtml;
            })
            .catch(error => {
                console.error('Помилка завантаження даних:', error);
                container.innerHTML = `<h2 style="color: red; text-align: center;">Помилка: Не вдалося завантажити автомобілі.</h2>`;
            });
    }

    function filterCars() {
        const selectedTagIds = Array.from(document.querySelectorAll('.tag-filter-checkbox:checked'))
                                    .map(checkbox => checkbox.value);

        let url = '/api/cars';

        if (selectedTagIds.length > 0) {
            const params = selectedTagIds.map(id => `tagIds=${id}`).join('&');
            url = `/api/cars?${params}`;
        }

        console.log('Запит фільтрації:', url);
        loadCars(url);
    }

    async function loadAndDisplayTags() {
        if (!tagFiltersContainer) return;
        tagFiltersContainer.innerHTML = '<p>Завантаження фільтрів...</p>';

        try {
            const response = await fetch('/api/tags');
            if (!response.ok) throw new Error('Failed to fetch tags');

            const tags = await response.json();
            console.log('Отримано теги:', tags);

            tagFiltersContainer.innerHTML = '';

            tags.forEach(tag => {
                const tagElement = document.createElement('label');
                tagElement.className = 'filter-tag';
                tagElement.innerHTML = `
                    <input type="checkbox" id="tag-${tag.id}" value="${tag.id}" class="tag-filter-checkbox">
                    <span class="tag-label">${tag.name}</span>
                `;
                tagFiltersContainer.appendChild(tagElement);
            });

            if (applyFiltersButton) {
                applyFiltersButton.addEventListener('click', filterCars);
            }

        } catch (error) {
            console.error('Error loading tags:', error);
            tagFiltersContainer.innerHTML = '<p>Не вдалося завантажити фільтри.</p>';
        }
    }
    loadAndDisplayTags();
    loadCars();
});