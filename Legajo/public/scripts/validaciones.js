
// Validación para Iniciar Sesión
document.addEventListener("DOMContentLoaded", () => {
    const formLogin = document.querySelector('form[action="dashboard_admin.html"]');
    if (formLogin) {
        formLogin.addEventListener("submit", function (e) {
            const email = formLogin.querySelector('input[type="email"]').value.trim();
            const password = formLogin.querySelector('input[type="password"]').value.trim();

            if (!validarEmail(email)) {
                alert("Por favor, ingresa un correo electrónico válido.");
                e.preventDefault();
                return;
            }

            if (password === "") {
                alert("Por favor, ingresa una contraseña.");
                e.preventDefault();
                return;
            }
        });
    }

    // Validación para Crear Cuenta
    const formRegistro = document.querySelector('input[name="Clave"]') ? document.querySelector('input[name="Clave"]').closest('form') : null;
    if (formRegistro) {
        formRegistro.addEventListener("submit", function (e) {
            const nombre = document.getElementById("NomUsu1").value.trim();
            const email = formRegistro.querySelector('input[type="email"]').value.trim();
            const password = formRegistro.querySelector('input[name="Clave"]').value;
            const confirmar = formRegistro.querySelector('input[name="Clave_confirmation"]').value;

            if (nombre === "") {
                alert("Por favor, ingresa tu primer nombre.");
                e.preventDefault();
                return;
            }

            if (!validarEmail(email)) {
                alert("Por favor, ingresa un correo electrónico válido.");
                e.preventDefault();
                return;
            }

            if (!validarContrasenaFuerte(password)) {
                alert("La contraseña debe tener más de 8 caracteres, al menos una mayúscula, una minúscula, un número y un carácter especial.");
                e.preventDefault();
                return;
            }

            if (password !== confirmar) {
                alert("Las contraseñas no coinciden.");
                e.preventDefault();
                return;
            }

            // Guardar nombre en localStorage si es necesario
            localStorage.setItem("textoCompartido", nombre);
        });
    }
});

// Función para validar formato de email
function validarEmail(correo) {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(correo);
}

// Función para validar contraseña fuerte
function validarContrasenaFuerte(contrasena) {
    // Más de 8 caracteres
    if (contrasena.length <= 8) return false;
    // Al menos una mayúscula
    if (!/[A-Z]/.test(contrasena)) return false;
    // Al menos una minúscula
    if (!/[a-z]/.test(contrasena)) return false;
    // Al menos un número
    if (!/\d/.test(contrasena)) return false;
    // Al menos un carácter especial
    if (!/[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/.test(contrasena)) return false;
    return true;
}
