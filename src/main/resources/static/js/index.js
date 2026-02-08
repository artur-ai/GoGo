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

    function loadReviews() {
            fetch('http://localhost:8080/api/reviews')
                .then(response => response.json())
                .then(reviews => {
                    displayReviews(reviews);
                })
                .catch(error => {
                    console.error('Помилка завантаження відгуків:', error);
                });
        }

        function displayReviews(reviews) {
                const reviewList = document.querySelector('.review-list');

                if (!reviewList) {
                    console.warn('Container .review-list not found');
                    return;
                }
        reviewList.innerHTML = '';
    reviews.forEach(review => {
                const reviewCard = document.createElement('div');
                reviewCard.className = 'review-card';

                reviewCard.innerHTML = `
                    <h3>${review.firstName}, ${calculateAge(review.dateOfBirth)} років, ${review.town}</h3>
                    <p>${review.reviewText}</p>
                `;

                reviewList.appendChild(reviewCard);
            });
        }
        function calculateAge(dateOfBirth) {
                const today = new Date();
                const birthDate = new Date(dateOfBirth);
                let age = today.getFullYear() - birthDate.getFullYear();
                const monthDiff = today.getMonth() - birthDate.getMonth();

                if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
                    age--;
                }

                return age;
            }

    loadFeaturedCars();
    loadReviews();
});