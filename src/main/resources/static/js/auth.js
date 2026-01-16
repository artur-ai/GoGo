const API_URL = 'http://localhost:8080/api/v1/auth';

const registerForm = document.getElementById('registration-form');

if (registerForm) {
    registerForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const formData = {
            firstName: document.getElementById('firstName').value,
            lastName: document.getElementById('lastName').value,
            middleName: document.getElementById('middleName').value,
            email: document.getElementById('email').value,
            password: document.getElementById('password').value,
            passportUrl: document.getElementById('passportUrl').value
        };

        try {
            const response = await fetch(`${API_URL}/register`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            });

            if (response.ok) {
                const data = await response.json();
                localStorage.setItem('jwtToken', data.token);
                localStorage.setItem('userName', data.firstName);

                document.getElementById('success-message').style.display = 'block';
                setTimeout(() => {
                    window.location.href = 'index.html';
                }, 1500);
            } else {
                alert('Помилка реєстрації! Можливо, такий email вже існує.');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('Сервер не відповідає. Перевір, чи запущено Spring Boot.');
        }
    });
}

const loginForm = document.getElementById('login-form');

if (loginForm) {
    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        try {
            const response = await fetch(`${API_URL}/authenticate`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ email, password })
            });

            if (response.ok) {
                const data = await response.json();
                localStorage.setItem('jwtToken', data.token);
                localStorage.setItem('userName', data.firstName);
                window.location.href = 'index.html';
            } else {
                alert('Невірний логін або пароль');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('Помилка з\'єднання з сервером');
        }
    });
}

function checkAuthStatus() {
    const token = localStorage.getItem('jwtToken');
    const name = localStorage.getItem('userName');

    const guestNav = document.getElementById('guest-nav');
    const userNav = document.getElementById('user-nav');
    const userNameSpan = document.getElementById('user-name-display');

    if (token && userNav && guestNav) {
        guestNav.style.display = 'none';
        userNav.style.display = 'flex';
       if(userNameSpan) {
                   userNameSpan.innerText = name;
               }
    } else if (userNav && guestNav) {
        guestNav.style.display = 'flex';
        userNav.style.display = 'none';
    }
}

function logout() {
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('userEmail');
    window.location.href = 'login.html';
}

document.addEventListener('DOMContentLoaded', checkAuthStatus);