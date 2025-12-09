   document.getElementById('registration-form').addEventListener('submit', function(event) {
        event.preventDefault();

        const form = event.target;
        const submitButton = form.querySelector('.cta-button');
        const successMessage = document.getElementById('success-message');

        const name = document.getElementById('name').value;
        const email = document.getElementById('email').value;
        const phone = document.getElementById('phone').value;
        const password = document.getElementById('password').value;

        const passportUrl = 'https://temp.storage.com/placeholder_' + Date.now() + '.jpg';

        const userData = {
            name: name,
            email: email,
            phone: phone,
            password: password,
            passportUrl: passportUrl
        };

        submitButton.disabled = true;
        successMessage.style.display = 'none';

        fetch('http://localhost:8080/api/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(userData)
        })
        .then(response => {
            submitButton.disabled = false;

            if (response.status === 201) {
                return response.json();
            } else if (response.status === 400 || response.status === 409) {
                 return response.json().then(errorData => {
                    alert('Помилка реєстрації: ' + JSON.stringify(errorData, null, 2));
                    throw new Error('Registration failed');
                 });
            } else {
                 throw new Error('Невідома помилка сервера (Код: ' + response.status + ')');
            }
        })
        .then(data => {
            console.log('Користувач успішно зареєстрований:', data);
            successMessage.textContent = 'Реєстрація успішна! ID: ' + data.id;
            successMessage.style.display = 'block';
            form.reset();
        })
        .catch(error => {
            submitButton.disabled = false;
            console.error('Помилка:', error);
            alert('Помилка підключення або сервера. Деталі в консолі.');
        });
    });