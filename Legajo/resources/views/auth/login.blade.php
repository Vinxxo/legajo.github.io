<x-guest-layout>
    <link rel="stylesheet" href="{{ asset('estilos/styles.css') }}">
    <button id="toggleModo" class="modo-toggle">☀️ Modo Claro</button>
    <section class="formulario formulario--narrow">
        <form method="POST" action="{{ route('login') }}">
            <div class="logo"></div>
            <h2>Iniciar sesión</h2>
            <!-- Session Status -->
            <x-auth-session-status class="mb-4" :status="session('status')" />

            @csrf

            <!-- Email Address -->
            <div>
                <x-input-label for="CorreoUsu" :value="__('Correo')" />
                <x-text-input id="CorreoUsu" class="block mt-1 w-full" type="email" name="CorreoUsu" :value="old('CorreoUsu')" placeholder="correo@ejemplo.com" required autofocus autocomplete="username" />
                <x-input-error :messages="$errors->get('CorreoUsu')" class="mt-2" />
            </div>

            <!-- Password -->
            <x-input-label for="Clave" :value="__('Clave')" />
            <x-text-input id="Clave" class="block mt-1 w-full" type="password" name="Clave" placeholder="contraseña" required autocomplete="current-password" />
            <x-input-error :messages="$errors->get('Clave')" class="mt-2" />

            <!-- Remember Me -->
            <div class="block mt-2">
                <label for="remember_me" class="inline-flex items-center">
                    <input id="remember_me" type="checkbox" name="remember">
                    <span class="ms-2 text-sm text-gray-600 dark:text-gray-400">{{ __('Remember me') }}</span>
                </label>
            </div>

            <div class="flex items-center justify-between mt-4">
                @if (Route::has('password.request'))
                    <a class="underline text-sm text-gray-600 dark:text-gray-400 hover:text-gray-900 dark:hover:text-gray-100 rounded-md focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 dark:focus:ring-offset-gray-800" href="{{ route('password.request') }}">
                        {{ __('Forgot your password?') }}
                    </a>
                @endif

                <x-primary-button class="ms-3">
                    {{ __('Log in') }}
                </x-primary-button>
            </div>
        </form>
        <script src="{{ asset('scripts/script.js') }}"></script>
        <script src="{{ asset('scripts/validaciones.js') }}"></script>
    </section>
</x-guest-layout>
