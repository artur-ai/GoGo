window.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('token');
    const firstName = localStorage.getItem('firstName');

    if (!token) {
        window.location.href = '/login.html';
        return;
    }

    const userNameDisplay = document.getElementById('user-name-display');
    if (userNameDisplay && firstName) {
        userNameDisplay.textContent = firstName;
    }
});

const form = document.getElementById('addCarForm');
const messageDiv = document.getElementById('message');

form.addEventListener('submit', async (e) => {
    e.preventDefault();

    showMessage('Додаю авто...', 'loading');

    const carData = {
        brand: document.getElementById('brand').value,
        model: document.getElementById('model').value,
        year: parseInt(document.getElementById('year').value),
        fuelType: document.getElementById('fuelType').value,
        engine: document.getElementById('engine').value,
        pricePerDay: parseFloat(document.getElementById('pricePerDay').value),
        pricePerMinute: parseFloat(document.getElementById('pricePerMinute').value),
        insurancePrice: parseFloat(document.getElementById('insurancePrice').value) || 0,
        imageUrl: document.getElementById('imageUrl').value || ""
    };

    console.log('Відправляю дані:', JSON.stringify(carData, null, 2));

    try {
        const token = localStorage.getItem('token');

        const response = await fetch('/api/v1/cars/admin/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(carData)
        });

        if (!response.ok) {
            if (response.status === 403) {
                showMessage('Доступ заборонено! Тільки адміни можуть додавати авто.', 'error');
                return;
            }
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const result = await response.json();
        showMessage(`✅ Авто "${result.brand} ${result.model}" успішно додано!`, 'success');
        form.reset();

        setTimeout(() => {
            messageDiv.style.display = 'none';
        }, 5000);

    } catch (error) {
        console.error('Error:', error);
        showMessage('Помилка: ' + error.message, 'error');
    }
});

function showMessage(text, type) {
    messageDiv.textContent = text;
    messageDiv.className = `message ${type}`;
    messageDiv.style.display = 'block';
}