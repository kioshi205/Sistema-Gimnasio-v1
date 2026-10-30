<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Dashboard de métricas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
</head>
<body>
<main class="dashboard">
    <header class="dashboard__header">
        <div>
            <h1>Dashboard de métricas</h1>
            <p>Resumen de ingresos, suscripciones y accesos del gimnasio</p>
        </div>
        <a class="dashboard__enlace" href="${pageContext.request.contextPath}/planes">Ir a planes</a>
    </header>

    <p class="aviso ${empty error ? 'aviso--oculto' : ''}">${error}</p>

    <section class="tarjetas" aria-label="Resumen del período">
        <article class="tarjeta">
            <p class="tarjeta__titulo">Ingresos del mes actual</p>
            <p class="tarjeta__valor" id="tarjeta-ingresos-mes">&mdash;</p>
        </article>
        <article class="tarjeta">
            <p class="tarjeta__titulo">Suscripciones nuevas del mes</p>
            <p class="tarjeta__valor" id="tarjeta-suscripciones-mes">&mdash;</p>
        </article>
        <article class="tarjeta">
            <p class="tarjeta__titulo">Ingresos a puerta de hoy</p>
            <p class="tarjeta__valor" id="tarjeta-ingresos-hoy">&mdash;</p>
        </article>
        <article class="tarjeta">
            <p class="tarjeta__titulo">Clientes nuevos del mes</p>
            <p class="tarjeta__valor" id="tarjeta-clientes-mes">&mdash;</p>
        </article>
    </section>

    <section class="graficos" aria-label="Gráficas de métricas">
        <article class="grafico">
            <h2>Ingresos (S/) por mes</h2>
            <p class="grafico__descripcion">Suscripciones no canceladas · últimos 12 meses</p>
            <div class="grafico__contenedor">
                <canvas id="grafico-ingresos"></canvas>
                <p class="grafico__vacio" id="vacio-ingresos" hidden>Sin datos para mostrar</p>
            </div>
        </article>

        <article class="grafico">
            <h2>Suscripciones nuevas por mes</h2>
            <p class="grafico__descripcion">Altas registradas · últimos 12 meses</p>
            <div class="grafico__contenedor">
                <canvas id="grafico-suscripciones"></canvas>
                <p class="grafico__vacio" id="vacio-suscripciones" hidden>Sin datos para mostrar</p>
            </div>
        </article>

        <article class="grafico">
            <h2>Ingresos a puerta por día</h2>
            <p class="grafico__descripcion">Accesos permitidos · últimos 30 días</p>
            <div class="grafico__contenedor">
                <canvas id="grafico-accesos"></canvas>
                <p class="grafico__vacio" id="vacio-accesos" hidden>Sin datos para mostrar</p>
            </div>
        </article>

        <article class="grafico">
            <h2>Clientes nuevos por mes</h2>
            <p class="grafico__descripcion">Registros de clientes · últimos 12 meses</p>
            <div class="grafico__contenedor">
                <canvas id="grafico-clientes"></canvas>
                <p class="grafico__vacio" id="vacio-clientes" hidden>Sin datos para mostrar</p>
            </div>
        </article>
    </section>
</main>

<script type="application/json" id="datos-dashboard">${datosJson}</script>
<script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.1/dist/chart.umd.min.js"></script>
<script>
    (function () {
        'use strict';

        var datos = JSON.parse(document.getElementById('datos-dashboard').textContent);
        var formatoMoneda = new Intl.NumberFormat('es-PE', {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
        });

        function ultimoValor(serie) {
            if (!serie || !serie.valores || serie.valores.length === 0) {
                return 0;
            }
            return Number(serie.valores[serie.valores.length - 1]) || 0;
        }

        function pintarTarjetas() {
            document.getElementById('tarjeta-ingresos-mes').textContent =
                'S/ ' + formatoMoneda.format(ultimoValor(datos.ingresosPorMes));
            document.getElementById('tarjeta-suscripciones-mes').textContent =
                String(ultimoValor(datos.suscripcionesPorMes));
            document.getElementById('tarjeta-ingresos-hoy').textContent =
                String(ultimoValor(datos.ingresosPuertaPorDia));
            document.getElementById('tarjeta-clientes-mes').textContent =
                String(ultimoValor(datos.clientesPorMes));
        }

        function crearGrafico(idCanvas, idVacio, serie, tipo, tituloX, tituloY, moneda) {
            var canvas = document.getElementById(idCanvas);
            var vacio = document.getElementById(idVacio);
            var etiquetas = (serie && serie.etiquetas) ? serie.etiquetas : [];
            var valores = (serie && serie.valores) ? serie.valores.map(Number) : [];
            var hayDatos = etiquetas.length > 0 && valores.some(function (valor) {
                return valor !== 0;
            });

            if (!hayDatos) {
                canvas.style.display = 'none';
                vacio.hidden = false;
                return;
            }

            canvas.style.display = 'block';
            vacio.hidden = true;

            new Chart(canvas.getContext('2d'), {
                type: tipo,
                data: {
                    labels: etiquetas,
                    datasets: [{
                        label: tituloY,
                        data: valores,
                        backgroundColor: tipo === 'line' ? 'rgba(6, 182, 212, 0.15)' : '#06b6d4',
                        borderColor: '#06b6d4',
                        borderWidth: 2,
                        pointBackgroundColor: '#06b6d4',
                        pointBorderColor: '#06b6d4',
                        fill: tipo === 'line',
                        tension: 0.3
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    scales: {
                        x: {
                            title: {
                                display: true,
                                text: tituloX,
                                color: '#0e7490'
                            },
                            ticks: { color: '#0e7490' },
                            grid: { color: 'rgba(6, 182, 212, 0.15)' },
                            border: { color: '#06b6d4' }
                        },
                        y: {
                            beginAtZero: true,
                            title: {
                                display: true,
                                text: tituloY,
                                color: '#0e7490'
                            },
                            ticks: {
                                color: '#0e7490',
                                precision: moneda ? undefined : 0
                            },
                            grid: { color: 'rgba(6, 182, 212, 0.15)' },
                            border: { color: '#06b6d4' }
                        }
                    },
                    plugins: {
                        legend: { display: false },
                        tooltip: {
                            backgroundColor: '#ffffff',
                            titleColor: '#06b6d4',
                            bodyColor: '#0e7490',
                            borderColor: '#06b6d4',
                            borderWidth: 1,
                            callbacks: {
                                label: function (contexto) {
                                    var valor = contexto.parsed.y;
                                    return moneda
                                        ? 'S/ ' + formatoMoneda.format(valor)
                                        : String(valor);
                                }
                            }
                        }
                    }
                }
            });
        }

        pintarTarjetas();
        crearGrafico('grafico-ingresos', 'vacio-ingresos', datos.ingresosPorMes,
            'bar', 'Mes', 'Ingresos (S/)', true);
        crearGrafico('grafico-suscripciones', 'vacio-suscripciones', datos.suscripcionesPorMes,
            'line', 'Mes', 'Suscripciones nuevas', false);
        crearGrafico('grafico-accesos', 'vacio-accesos', datos.ingresosPuertaPorDia,
            'bar', 'Día', 'Ingresos permitidos', false);
        crearGrafico('grafico-clientes', 'vacio-clientes', datos.clientesPorMes,
            'bar', 'Mes', 'Clientes nuevos', false);
    })();
</script>
</body>
</html>