 const burger = document.getElementById('burger');
    const navLinks = document.querySelector('.nav-links');

    burger.addEventListener('click', () => {
      navLinks.classList.toggle('active');
    });

      function renderCarCard(car) {

      const bookingUrl = `Cars-booking/${car.model}.html`;

      const carHTML = `
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
          return carHTML;
          }

    function loadCars() {
        const container = document.getElementById('car-list-container');
        if (!container) return;

        container.innerHTML = '<h2 style="text-align: center;">Завантаження каталогу...</h2>';

        fetch('/api/cars')
                  .then(response => {
                      if (!response.ok) {
                          throw new Error('HTTP error! Status: ' + response.status);
                      }
                      return response.json();
                  })
                  .then(cars => {
                      container.innerHTML = '';
                      cars.forEach(car => {
                          container.innerHTML += renderCarCard(car);
                      });
                  })
                  .catch(error => {
                      console.error('Помилка завантаження даних:', error);
                      container.innerHTML = `<h2 style="color: red; text-align: center;">Помилка: Не вдалося завантажити автомобілі.</h2>`;
                  });

    }

    document.addEventListener('DOMContentLoaded', loadCars);


