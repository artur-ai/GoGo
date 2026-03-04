window.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('token');

    if (!token) {
        window.location.href = '/login.html';
        return;
    }
});

const form = document.getElementById('addCarForm');
const messageDiv = document.getElementById('message');
const logoutBtn = document.getElementById('logoutBtn');

form.addEventListener('submit', async (e) => {
    e.preventDefault();

    showMessage('Adding car...', 'loading');

    const carData = {
        brand: document.getElementById('brand').value,
        model: document.getElementById('model').value,
        year: parseInt(document.getElementById('year').value),
        fuelType: document.getElementById('fuelType').value,
        engine: document.getElementById('engine').value,
        pricePerDay: parseFloat(document.getElementById('pricePerDay').value),
        pricePerMinute: parseFloat(document.getElementById('pricePerMinute').value),
        insurancePrice: parseFloat(document.getElementById('insurancePrice').value) || 0,
        imageUrl: document.getElementById('imageUrl').value || null
    };

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
                showMessage('Access denied! Only admins can add cars.', 'error');
                return;
            }
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const result = await response.json();
        showMessage(`✅ Car "${result.brand} ${result.model}" added successfully! Check your email for notifications.`, 'success');
        form.reset();

        setTimeout(() => {
            messageDiv.style.display = 'none';
        }, 5000);

    } catch (error) {
        console.error('Error:', error);
        showMessage('Error adding car: ' + error.message, 'error');
    }
});

logoutBtn.addEventListener('click', () => {
    localStorage.removeItem('token');
    localStorage.removeItem('firstName');
    window.location.href = '/login.html';
});

function showMessage(text, type) {
    messageDiv.textContent = text;
    messageDiv.className = `message ${type}`;
    messageDiv.style.display = 'block';
}