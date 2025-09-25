<?php

namespace App\Http\Middleware;

use Closure;
use Illuminate\Http\Request;
use Symfony\Component\HttpFoundation\Response;

class PreventBackHistory
{
    /**
     * Handle an incoming request.
     */
    public function handle(Request $request, Closure $next): Response
    {
        $response = $next($request);

        // Invalidate session on logout to prevent back button access
        if (!$request->user()) {
            $request->session()->invalidate();
            $request->session()->regenerateToken();
        }

        // Forzar redirección a login si no está autenticado y accede a ruta protegida
        if (!$request->user() && $request->is('admin/*', 'usuario/*', 'profile', 'dashboard')) {
            return redirect()->route('login');
        }

        // Evita que el navegador cachee páginas protegidas
        return $response->header('Cache-Control', 'no-store, no-cache, must-revalidate, max-age=0')
                        ->header('Pragma', 'no-cache')
                        ->header('Expires', 'Sat, 01 Jan 2000 00:00:00 GMT');
    }
}



