<x-guest-layout>
    <link rel="stylesheet" href="{{ asset('estilos/styles.css') }}">
    <button id="toggleModo" class="modo-toggle">☀️ Modo Claro</button>
    <section class="formulario">
        <form method="POST" action="{{ route('register') }}">
            <div class="logo"></div>
            <h2>Crear cuenta</h2>
            @csrf

            <div class="grid-2cols">
                <div>
                    <!-- Primer Nombre -->
                    <div>
                        <x-input-label for="NomUsu1" :value="__('Primer Nombre')" />
                        <x-text-input id="NomUsu1" class="block w-full" type="text" name="NomUsu1" :value="old('NomUsu1')" placeholder="Ingresa tu primer nombre" required autofocus />
                        <x-input-error :messages="$errors->get('NomUsu1')" class="mt-2" />
                    </div>

                    <!-- Segundo Nombre -->
                    <div>
                        <x-input-label for="NomUsu2" :value="__('Segundo Nombre')" />
                        <x-text-input id="NomUsu2" class="block w-full" type="text" name="NomUsu2" :value="old('NomUsu2')" placeholder="Opcional" />
                        <x-input-error :messages="$errors->get('NomUsu2')" class="mt-2" />
                    </div>

                    <!-- Primer Apellido -->
                    <div>
                        <x-input-label for="ApeUsu1" :value="__('Primer Apellido')" />
                        <x-text-input id="ApeUsu1" class="block w-full" type="text" name="ApeUsu1" :value="old('ApeUsu1')" placeholder="Ingresa tu primer apellido" required />
                        <x-input-error :messages="$errors->get('ApeUsu1')" class="mt-2" />
                    </div>

                    <!-- Segundo Apellido -->
                    <div>
                        <x-input-label for="ApeUsu2" :value="__('Segundo Apellido')" />
                        <x-text-input id="ApeUsu2" class="block w-full" type="text" name="ApeUsu2" :value="old('ApeUsu2')" placeholder="Opcional" />
                        <x-input-error :messages="$errors->get('ApeUsu2')" class="mt-2" />
                    </div>
                </div>

                <div>
                    <!-- Correo -->
                    <div>
                        <x-input-label for="CorreoUsu" :value="__('Correo')" />
                        <x-text-input id="CorreoUsu" class="block w-full" type="email" name="CorreoUsu" :value="old('CorreoUsu')" placeholder="correo@ejemplo.com" required />
                        <x-input-error :messages="$errors->get('CorreoUsu')" class="mt-2" />
                    </div>

                    <!-- Clave -->
                    <div>
                        <x-input-label for="Clave" :value="__('Contraseña')" />
                        <x-text-input id="Clave" class="block w-full" type="password" name="Clave" placeholder="Mínimo 8 caracteres" required />
                        <x-input-error :messages="$errors->get('Clave')" class="mt-2" />
                    </div>

                    <!-- Confirmar Clave -->
                    <div>
                        <x-input-label for="Clave_confirmation" :value="__('Confirmar Contraseña')" />
                        <x-text-input id="Clave_confirmation" class="block w-full" type="password" name="Clave_confirmation" placeholder="Repite tu contraseña" required />
                        <x-input-error :messages="$errors->get('Clave_confirmation')" class="mt-2" />
                    </div>

                    <!-- Rol -->
                    <div>
                        <x-input-label for="FK_roles" :value="__('Rol')" />
                        <select id="FK_roles" name="FK_roles" class="block mt-1 w-full" required>
                            <option value="2">Usuario</option>
                            <option value="1">Administrador</option>
                        </select>
                        <x-input-error :messages="$errors->get('FK_roles')" class="mt-2" />
                    </div>
                </div>
            </div>

            <!-- Dirección, Ciudad, Teléfono en fila -->
            <div class="grid-3cols mt-4">
                <div class="col-span-2">
                    <x-input-label for="DireccionUsu" :value="__('Dirección')" />
                    <x-text-input id="DireccionUsu" class="block w-full" type="text" name="DireccionUsu" :value="old('DireccionUsu')" placeholder="Calle, número, piso" />
                    <x-input-error :messages="$errors->get('DireccionUsu')" class="mt-2" />
                </div>
                <div>
                    <x-input-label for="CiudadUsu" :value="__('Ciudad')" />
                    <x-text-input id="CiudadUsu" class="block w-full" type="text" name="CiudadUsu" :value="old('CiudadUsu')" placeholder="Ciudad" />
                    <x-input-error :messages="$errors->get('CiudadUsu')" class="mt-2" />
                </div>
                <div>
                    <x-input-label for="TelefonoUsu" :value="__('Teléfono')" />
                    <x-text-input id="TelefonoUsu" class="block w-full" type="text" name="TelefonoUsu" :value="old('TelefonoUsu')" placeholder="Ej: +57 300 000 0000" />
                    <x-input-error :messages="$errors->get('TelefonoUsu')" class="mt-2" />
                </div>
            </div>

            <div class="flex items-center justify-between mt-4">
                <a class="underline text-sm text-gray-600 dark:text-gray-400 hover:text-gray-900 dark:hover:text-gray-100" href="{{ route('login') }}">
                    {{ __('¿Ya estás registrado?') }}
                </a>

                <x-primary-button class="ms-4">
                    {{ __('Registrar') }}
                </x-primary-button>
            </div>
        </form>
        <script src="{{ asset('scripts/script.js') }}"></script>
        <script src="{{ asset('scripts/validaciones.js') }}"></script>
    </section>
</x-guest-layout>
