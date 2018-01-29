
(function() {
    'use strict';

    // Disable warning "Synchronous XMLHttpRequest on the main thread is deprecated.."
    $.ajaxPrefilter(function(options) {
        options.async = true;
    });

    // used for the preloader
    $(function() { document.body.style.opacity = 1; });

})();

(function() {
    'use strict';

    $(FlotCharts);

    function FlotCharts() {

        if (!$.fn.plot) return;

        // Dont run if charts page not loaded
        if (!$('#bar-flotchart').length) return;

     




        // Generate random data for realtime demo
        var data = [],
            totalPoints = 300;

        var realTimeData = getRandomData();
        update();

        function getRandomData() {
            if (data.length > 0)
                data = data.slice(1);
            // Do a random walk
            while (data.length < totalPoints) {
                var prev = data.length > 0 ? data[data.length - 1] : 50,
                    y = prev + Math.random() * 10 - 5;
                if (y < 0) {
                    y = 0;
                } else if (y > 100) {
                    y = 100;
                }
                data.push(y);
            }
            // Zip the generated y values with the x values
            var res = [];
            for (var i = 0; i < data.length; ++i) {
                res.push([i, data[i]]);
            }
            return [res];
        }

        function update() {
            realTimeData = getRandomData();
            $('#realtime-flotchart').plot(realTimeData, realTimeOptions);
            setTimeout(update, 30);
        }
        // end random data generation
    }

})();
(function() {
    'use strict';

    $(initPeity);

    function initPeity() {

        if (!$.fn.peity) return;

        $('.peity-pie').peity('pie', {
            radius: 25,
            fill: [Colors.byName('deepPurple-100'), Colors.byName('deepPurple-400'), Colors.byName('deepPurple-700')]
        });

        $('.peity-donut').peity('donut', {
            radius: 25,
            fill: [Colors.byName('pink-100'), Colors.byName('pink-400'), Colors.byName('pink-700')]
        });

        $('.peity-line').peity('line', {
            height: 40,
            width: 100,
            strokeWidth: 3,
            stroke: Colors.byName('teal-500'),
            fill: Colors.byName('teal-100')
        });

        $('.peity-bar').peity('bar', {
            height: 40,
            width: 100,
            fill: [Colors.byName('cyan-100'), Colors.byName('cyan-400'), Colors.byName('cyan-700')]
        });

        // Real time example

        var updatingChart = $('.realtime-peity-chart').peity('line', {
            fill: Colors.byName('green-200'),
            stroke: Colors.byName('green-500'),
            width: '100%',
            height: 60
        });

        setInterval(function() {
            var random = Math.round(Math.random() * 10);
            var values = updatingChart.text().split(',');
            values.shift();
            values.push(random);

            updatingChart
                .text(values.join(','))
                .change();
        }, 1000);

    }

})();



(function(global) {
    'use strict';

    global.APP_COLORS = {
        'gray-darker':            '#263238',
        'gray-dark':              '#455A64',
        'gray':                   '#607D8B',
        'gray-light':             '#90A4AE',
        'gray-lighter':           '#ECEFF1',

        'primary':                '#448AFF',
        'success':                '#4CAF50',
        'info':                   '#03A9F4',
        'warning':                '#FFB300',
        'danger':                 '#F44336'
    };

})(window);

(function(global) {
    'use strict';

    global.Colors = new ColorsHandler();

    function ColorsHandler() {
        this.byName = byName;

        ////////////////

        function byName(name) {
            var color = APP_COLORS[name];
            if (!color && (typeof materialColors !== 'undefined')) {
                var c = name.split('-'); // red-500, blue-a100, deepPurple-500, etc
                if (c.length)
                    color = (materialColors[c[0]] || {})[c[1]];
            }
            return (color || '#fff');
        }
    }

})(window);
(function() {
    'use strict';

    $(initDashboard);

    function initDashboard() {

        if (!$.fn.plot || !$.fn.knob) return;

        var knobLoaderOptions1 = {
            width: '80%', // responsive
            displayInput: true,
            fgColor: Colors.byName('primary'),
            bgColor: 'rgba(162,162,162, .09)',
            angleOffset: -125,
            angleArc: 250,
            readOnly: true
        };

        $('#dash-chart1').knob(knobLoaderOptions1);

        // Simulate real time knob chart
        setInterval(function() {
            var endValue = Math.floor(Math.random() * 20) + 20;
            var dial = $('#dash-chart1');
            dial.animate({ value: endValue }, {
                duration: 1000,
                easing: 'swing',
                step: function(now, fx) {
                    fx.now = parseInt(now);
                    dial.val(Math.floor(this.value)).trigger('change');
                }
            });
        }, 2000);

        // Animate progress bars in real time
        var ram = $('#ram'),
            ramvalue = $('#ram-value'),
            io = $('#io'),
            iovalue = $('#io-value');
        setInterval(function() {
            var r = (Math.floor(Math.random() * 20) + 40) + '%';
            var i = (Math.floor(Math.random() * 10) + 20) + '%';
            ramvalue.text(r);
            iovalue.text(i);
            ram.css({ width: r });
            io.css({ width: i });
        }, 4000);

        // Animate counting of numbers
        $('[data-counter]').each(function() {
            var $this = $(this);
            $this.prop('counter', 0).animate({
                counter: $this.data('counter')
            }, {
                duration: 3000,
                easing: 'swing',
                step: function(now) {
                    $this.text(numberWithCommas(Math.ceil(now)));
                }
            });
        });

        function numberWithCommas(x) { // https://stackoverflow.com/a/2901298
            return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        }

        // Main Flot chart
        var splineData = [{
            'label': 'Unique',
            'color': Colors.byName('blue-400'),
            data: [
                ['5', 50],
                ['6', 70],

                ['7', 60],
                ['8', 120],
                ['9', 80],

                ['10', 150],
                ['11', 80],
                ['12', 90]
            ]
        }];
        var splineOptions = {
            series: {
                lines: {
                    show: true,
                    fill: true,
                    fillColor: { colors: [{ opacity: 0 }, { opacity: 1 }] }
                },
                points: {
                    show: true,
                    radius: 3
                }
            },
            grid: {
                borderColor: '#eee',
                borderWidth: 0,
                hoverable: true,
                backgroundColor: 'transparent'
            },
            tooltip: true,
            tooltipOpts: {
                content: function(label, x, y) {
                    return x + ' : ' + y;
                }
            },
            xaxis: {
                show: false,
                tickColor: 'transparent',
                mode: 'categories',
                font: {
                    color: Colors.byName('blueGrey-200')
                }
            },
            yaxis: {
                show: false,
                min: 0,
                max: 180, // optional: use it for a clear representation
                tickColor: 'transparent',
                font: {
                    color: Colors.byName('blueGrey-200')
                },
                //position: 'right' or 'left',
                tickFormatter: function(v) {
                    return v /* + ' visitors'*/ ;
                }
            },
            legend: false,
            shadowSize: 0
        };

        $('#flot-main-spline').each(function() {
            var $el = $(this);
            if ($el.data('height')) $el.height($el.data('height'));
            $el.plot(splineData, splineOptions);
        });


        // Bar chart stacked
        // ------------------------
        var stackedChartData = [{
            data: [
                ['1.2', 45],
                ['2.5', 47],
                ['3.0', 45],
                ['4.2', 42],
                ['4.5', 45],
                ['4.7', 42],
                ['5.0', 45]
            ]
        }, {
            data: [
                ['1.2', 30],
                ['2.5', 27],
                ['3.0', 35],
                ['4.2', 25],
                ['4.5', 35],
                ['4.7', 35],
                ['5.0', 17]
            ]
        }];

        var stackedChartOptions = {
            bars: {
                show: true,
                fill: true,
                barWidth: 0.3,
                lineWidth: 1,
                align: 'center',
                // order : 1,
                fillColor: {
                    colors: [{
                        opacity: 1
                    }, {
                        opacity: 1
                    }]
                }
            },
            colors: [Colors.byName('deepPurple-100'), Colors.byName('deepPurple-300')],
            series: {
                shadowSize: 3
            },
            xaxis: {
                show: true,
                position: 'bottom',
                mode: 'categories'
            },
            yaxis: {
                show: false,
                min: 0,
                max: 60
            },
            grid: {
                hoverable: true,
                clickable: true,
                borderWidth: 0,
                color: 'rgba(120,120,120,0.5)'
            },
            tooltip: true,
            tooltipOpts: {
                content: 'Value %x.0 is %y.0',
                defaultTheme: false,
                shifts: {
                    x: 0,
                    y: -20
                }
            }
        };

        $('#flot-stacked-chart').each(function() {
            var $el = $(this);
            if ($el.data('height')) $el.height($el.data('height'));
            $el.plot(stackedChartData, stackedChartOptions);
        });


        // Flot bar chart
        // ------------------
        var barChartOptions = {
            series: {
                bars: {
                    show: true,
                    fill: 1,
                    barWidth: 0.2,
                    lineWidth: 0,
                    align: 'center'
                },
                highlightColor: 'rgba(255,255,255,0.2)'
            },
            grid: {
                hoverable: true,
                clickable: true,
                borderWidth: 0,
                color: '#8394a9'
            },
            tooltip: true,
            tooltipOpts: {
                content: function getTooltip(label, x, y) {
                    return 'Activity for ' + x + ': ' + (y * 1000);
                }
            },
            xaxis: {
                tickColor: 'transparent',
                mode: 'categories',
                font: {
                    color: Colors.byName('blueGrey-200')
                }
            },
            yaxis: {
                show: false,
                tickColor: 'transparent',
                font: {
                    color: Colors.byName('blueGrey-200')
                }
            },
            legend: {
                show: false
            },
            shadowSize: 0
        };

        var barChartData = [{
            'label': '2017',
            bars: {
                order: 0,
                fillColor: { colors: [Colors.byName('blue-100'), Colors.byName('purple-100')] }
            },
            data: [
                ['Jan', 30],
                ['Feb', 25],
                ['Mar', 30],
                ['Apr', 35],
                ['May', 5]
            ]
        }, {
            'label': '2016',
            bars: {
                order: 1,
                fillColor: { colors: [Colors.byName('blue-500'), Colors.byName('purple-400')] }
            },
            data: [
                ['Jan', 45],
                ['Feb', 35],
                ['Mar', 25],
                ['Apr', 50],
                ['May', 20]
            ]
        }];

        $('#flot-bar-chart').each(function() {
            var $el = $(this);
            if ($el.data('height')) $el.height($el.data('height'));
            $el.plot(barChartData, barChartOptions);
        });

        // Small flot chart
        // ---------------------
        var chartTaskData = [{
            'label': 'Total',
            color: Colors.byName('primary'),
            data: [
                ['Jan', 14],
                ['Feb', 14],
                ['Mar', 12],
                ['Apr', 16],
                ['May', 13],
                ['Jun', 14],
                ['Jul', 19]
                //4, 4, 3, 5, 3, 4, 6
            ]
        }];
        var chartTaskOptions = {
            series: {
                lines: {
                    show: false
                },
                points: {
                    show: false
                },
                splines: {
                    show: true,
                    tension: 0.4,
                    lineWidth: 3,
                    fill: 1
                },
            },
            legend: {
                show: false
            },
            grid: {
                show: false,
            },
            tooltip: true,
            tooltipOpts: {
                content: function(label, x, y) {
                    return x + ' : ' + y;
                }
            },
            xaxis: {
                tickColor: '#fcfcfc',
                mode: 'categories'
            },
            yaxis: {
                min: 0,
                max: 30, // optional: use it for a clear representation
                tickColor: '#eee',
                //position: 'right' or 'left',
                tickFormatter: function(v) {
                    return v /* + ' visitors'*/ ;
                }
            },
            shadowSize: 0
        };

        $('#flot-task-chart').each(function() {
            var $el = $(this);
            if ($el.data('height')) $el.height($el.data('height'));
            $el.plot(chartTaskData, chartTaskOptions);
        });

        // Donut chart
        // -----------------
        var donutData = [{
            'color': Colors.byName('blue-200'),
            'data': 60,
            'label': 'Users'
        }, {
            'color': Colors.byName('blue-300'),
            'data': 90,
            'label': 'System'
        }, {
            'color': Colors.byName('blue-400'),
            'data': 50,
            'label': 'Memory'
        }, {
            'color': Colors.byName('blue-500'),
            'data': 80,
            'label': 'Server'
        }, {
            'color': Colors.byName('blue-600'),
            'data': 116,
            'label': 'Database'
        }];
        var donutOptions = {
            series: {
                pie: {
                    stroke: {
                        width: 0,
                        color: '#a1a1a1'
                    },
                    show: true,
                    innerRadius: 0.5 // This makes the donut shape
                }
            },
            legend: {
                show: false
            }
        };

        $('#donut-dashboard').plot(donutData, donutOptions);


        // Sparklines
        // -----------------

        var sparkValue1 = [4, 2, 3, 5, 3, 2, 3, 4, 6];
        var sparkValue2 = [5, 3, 4, 6, 5, 3, 4, 3, 4];
        var sparkValue3 = [4, 3, 4, 5, 3, 2, 3, 4, 6];
        var sparkOpts = {
            chartRangeMin: 0,
            type: 'bar',
            height: 50,
            width: '90',
            lineWidth: 4,
            barSpacing: 8,
            valueSpots: {
                '0:': Colors.byName('blue-700'),
            },
            lineColor: Colors.byName('blue-700'),
            spotColor: Colors.byName('blue-700'),
            fillColor: 'transparent',
            highlightLineColor: Colors.byName('blue-700'),
            spotRadius: 0
        };

        initSparkline($('#sparkline1'), sparkValue1, sparkOpts);
        initSparkline($('#sparkline2'), sparkValue2, sparkOpts);
        initSparkline($('#sparkline3'), sparkValue3, sparkOpts);
        // call sparkline and mix options with data attributes
        function initSparkline(el, values, opts) {
            el.sparkline(values, $.extend(opts, el.data()));
        }

        if (document.getElementById('dashboard-map')) {
            var MapStyles = [{ featureType: 'water', stylers: [{ visibility: 'on' }, { color: '#bdd1f9' }] }, { featureType: 'all', elementType: 'labels.text.fill', stylers: [{ color: '#334165' }] }, { featureType: 'landscape', stylers: [{ color: '#e9ebf1' }] }, { featureType: 'road.highway', elementType: 'geometry', stylers: [{ color: '#c5c6c6' }] }, { featureType: 'road.arterial', elementType: 'geometry', stylers: [{ color: '#fff' }] }, { featureType: 'road.local', elementType: 'geometry', stylers: [{ color: '#fff' }] }, { featureType: 'transit', elementType: 'geometry', stylers: [{ color: '#d8dbe0' }] }, { featureType: 'poi', elementType: 'geometry', stylers: [{ color: '#cfd5e0' }] }, { featureType: 'administrative', stylers: [{ visibility: 'on' }, { lightness: 33 }] }, { featureType: 'poi.park', elementType: 'labels', stylers: [{ visibility: 'on' }, { lightness: 20 }] }, { featureType: 'road', stylers: [{ color: '#d8dbe0', lightness: 20 }] }];
            var map = new GMaps({
                div: '#dashboard-map',
                lat: 43.102416,
                lng: -76.144695,
                disableDefaultUI: true,
                scrollwheel: false,
                zoom: 12
            });
            map.addMarker({
                lat: 43.102416,
                lng: -76.144695,
                title: 'You',
                icon: 'img/marker.png',
                click: function() {}
            });
            map.addStyle({
                styledMapName: 'Styled Map',
                styles: MapStyles,
                mapTypeId: 'map_style'
            });
            map.setStyle('map_style');
            // Enable Google Maps Directions API to use Routes
            map.drawRoute({
                origin: [43.102416, -76.144695],
                destination: [43.103419, -76.060238],
                travelMode: 'walking',
                strokeColor: Colors.byName('green-500'),
                strokeOpacity: 1,
                strokeWeight: 6
            });
        }

    }
})();
(function() {
    'use strict';

    $(runBootstrap);

    function runBootstrap() {

        // POPOVER
        // -----------------------------------

        $('[data-toggle="popover"]').popover();

        // TOOLTIP
        // -----------------------------------

        $('[data-toggle="tooltip"]').tooltip({
            container: 'body',
            animation: false // https://github.com/twbs/bootstrap/issues/21607#issuecomment-287533209
        });

    }

})();


(function() {
    'use strict';

    $(initNestable);

    function initNestable() {
        var updateOutput = function(e) {
            var list = e.length ? e : $(e.target),
                output = list.data('output');
            if (window.JSON) {
                output.text(window.JSON.stringify(list.nestable('serialize'))); //, null, 2));
            } else {
                output.text('JSON browser support required for this demo.');
            }
        };

        // activate Nestable for list 1
        $('#nestable').each(function() {
            $(this).nestable({
                group: 1
            })
            .on('change', updateOutput);

            // output initial serialised data
            updateOutput($(this).data('output', $('#nestable-output')));
        });

        // activate Nestable for list 2
        $('#nestable2').each(function() {
            $(this).nestable({
                group: 1
            })
            .on('change', updateOutput);

            // output initial serialised data
            updateOutput($(this).data('output', $('#nestable-output2')));
        });

        $('.js-nestable-action').on('click', function(e) {
            var target = $(e.target),
                action = target.data('action');
            if (action === 'expand-all') {
                $('.dd').nestable('expandAll');
            }
            if (action === 'collapse-all') {
                $('.dd').nestable('collapseAll');
            }
        });
    }

})();

(function() {
    'use strict';

    $(runSweetAlert);

    function runSweetAlert() {

        $('#swal-demo1').on('click', function(e) {
            e.preventDefault();
            swal('Here\'s a message!');
        });

        $('#swal-demo2').on('click', function(e) {
            e.preventDefault();
            swal('Here\'s a message!', 'It\'s pretty, isn\'t it?');
        });

        $('#swal-demo3').on('click', function(e) {
            e.preventDefault();
            swal('Good job!', 'You clicked the button!', 'success');
        });

        $('#swal-demo4').on('click', function(e) {
            e.preventDefault();
            swal({
                    title: 'Are you sure?',
                    text: 'You will not be able to recover this imaginary file!',
                    type: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#DD6B55',
                    confirmButtonText: 'Yes, delete it!',
                    closeOnConfirm: false
                },
                function() {
                    swal('Deleted!', 'Your imaginary file has been deleted.', 'success');
                });

        });

        $('#swal-demo5').on('click', function(e) {
            e.preventDefault();
            swal({
                title: 'Are you sure?',
                text: 'You will not be able to recover this imaginary file!',
                type: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#DD6B55',
                confirmButtonText: 'Yes, delete it!',
                cancelButtonText: 'No, cancel plx!',
                closeOnConfirm: false,
                closeOnCancel: false
            }, function(isConfirm) {
                if (isConfirm) {
                    swal('Deleted!', 'Your imaginary file has been deleted.', 'success');
                } else {
                    swal('Cancelled', 'Your imaginary file is safe :)', 'error');
                }
            });

        });
    }

})();
(function() {
    'use strict';

    $(runToaster);

    function runToaster() {

        $('#clear-toaster').click(function(){
            toastr.clear();
        });

        $('#top-right-info').click(showToaster('info', 'toast-top-right'));
        $('#top-left-info').click(showToaster('info', 'toast-top-left'));
        $('#bottom-right-info').click(showToaster('info', 'toast-bottom-right'));
        $('#bottom-left-info').click(showToaster('info', 'toast-bottom-left'));

        $('#top-right-success').click(showToaster('success', 'toast-top-right'));
        $('#top-left-success').click(showToaster('success', 'toast-top-left'));
        $('#bottom-right-success').click(showToaster('success', 'toast-bottom-right'));
        $('#bottom-left-success').click(showToaster('success', 'toast-bottom-left'));

        $('#top-right-warning').click(showToaster('warning', 'toast-top-right'));
        $('#top-left-warning').click(showToaster('warning', 'toast-top-left'));
        $('#bottom-right-warning').click(showToaster('warning', 'toast-bottom-right'));
        $('#bottom-left-warning').click(showToaster('warning', 'toast-bottom-left'));

        $('#top-right-error').click(showToaster('error', 'toast-top-right'));
        $('#top-left-error').click(showToaster('error', 'toast-top-left'));
        $('#bottom-right-error').click(showToaster('error', 'toast-bottom-right'));
        $('#bottom-left-error').click(showToaster('error', 'toast-bottom-left'));

        function showToaster(type, positionClass) {
            return function() {
                toastr.options.positionClass = positionClass;
                toastr[type]('My name is Inigo Montoya. You killed my father, prepare to die!');
            };
        }

    }
})();


(function() {
    'use strict';

    $(initHeader);

    function initHeader() {

        // Search modal
        var modalSearch = $('.modal-search');
        $('#header-search, .open-header-search').on('click', function(e) {
            e.preventDefault();
            modalSearch
                .on('show.bs.modal', function() {
                    // Add class for white backdrop
                    $('body').addClass('modal-backdrop-soft');
                })
                .on('hidden.bs.modal', function() {
                    // Remove class for white backdrop (if not will affect future modals)
                    $('body').removeClass('modal-backdrop-soft');
                })
                .on('shown.bs.modal', function() {
                    // Auto focus the search input
                    $('.header-input-search').focus();
                })
                .modal()
                ;
        });

        // Settings modal
        var modalSettings = $('.modal-settings');
        $('#header-settings').on('click', function(){
            modalSettings
                .on('show.bs.modal', function() {
                    // Add class for soft backdrop
                    $('body').addClass('modal-backdrop-soft');
                })
                .on('hidden.bs.modal', function() {
                    // Remove class for soft backdrop (if not will affect future modals)
                    $('body').removeClass('modal-backdrop-soft');
                })
                .modal()
                ;
        });

    }

})();
(function() {
    'use strict';

    if (typeof Dropzone === 'undefined') return;

    // Prevent Dropzone from auto discovering
    // This is useful when you want to create the
    // Dropzone programmatically later
    Dropzone.autoDiscover = false;

    $(formUpload);

    function formUpload() {

        // Dropzone settings
        var dropzoneOptions = {
            autoProcessQueue: false,
            uploadMultiple: true,
            parallelUploads: 100,
            maxFiles: 100,
            dictDefaultMessage: '<em class="ion-upload color-blue-grey-100 icon-2x"></em><br>Drop files here to upload', // default messages before first drop
            paramName: 'file', // The name that will be used to transfer the file
            maxFilesize: 2, // MB
            addRemoveLinks: true,
            accept: function(file, done) {
                if (file.name === 'justinbieber.jpg') {
                    done('Naha, you dont. :)');
                } else {
                    done();
                }
            },
            init: function() {
                var dzHandler = this;

                this.element.querySelector('button[type=submit]').addEventListener('click', function(e) {
                    e.preventDefault();
                    e.stopPropagation();
                    dzHandler.processQueue();
                });
                this.on('addedfile', function(file) {
                    console.log('Added file: ' + file.name);
                });
                this.on('removedfile', function(file) {
                    console.log('Removed file: ' + file.name);
                });
                this.on('sendingmultiple', function() {

                });
                this.on('successmultiple', function( /*files, response*/ ) {

                });
                this.on('errormultiple', function( /*files, response*/ ) {

                });
            }

        };

        var dropzoneArea = new Dropzone('#dropzone-area', dropzoneOptions);

    }

})();
(function() {
    'use strict';

    $(formAdvanced);

    function formAdvanced() {

        if (!$.fn.select2 ||
            !$.fn.datepicker ||
            !$.fn.clockpicker ||
            !$.fn.colorpicker) return;

        // Select 2

        $('#select2-1').select2();
        $('#select2-2').select2();
        $('#select2-3').select2({
            placeholder: 'Select a state',
            allowClear: true
        });
        $('#select2-4').select2({
            data: [{ id: 0, text: 'enhancement' }, { id: 1, text: 'bug' }, { id: 2, text: 'duplicate' }, { id: 3, text: 'invalid' }, { id: 4, text: 'wontfix' }]
        });

        // Datepicker

        $('#example-datepicker-1').datepicker({ autoclose: true });
        $('#example-datepicker-2').datepicker({ autoclose: true });
        $('#example-datepicker-3').datepicker({ autoclose: true });
        $('#example-datepicker-4')
            .datepicker({
                autoclose: true,
                container: '#example-datepicker-container-4'
            });
        $('#example-datepicker-5')
            .datepicker({
                autoclose: true,
                container: '#example-datepicker-container-5'
            });

        // Clockpicker
        var cpInput = $('.clockpicker').clockpicker();
        // auto close picker on scroll
        $('main').scroll(function() {
            cpInput.clockpicker('hide');
        });

        // MultiSelect

        $('#multiselect1').multiSelect();
        $('#optgroup').multiSelect({ selectableOptgroup: true });
        // Public Methods
        var publicMethods = $('#public-methods').multiSelect();
        $('#select-all').click(function() {
            publicMethods.multiSelect('select_all');
            return false;
        });
        $('#deselect-all').click(function() {
            publicMethods.multiSelect('deselect_all');
            return false;
        });
        var demoValues = ['elem_0', 'elem_1', 'elem_2', 'elem_3', 'elem_4', 'elem_5', 'elem_6', 'elem_7', 'elem_8', 'elem_9'];
        $('#select-100').click(function() {
            publicMethods.multiSelect('select', demoValues);
            return false;
        });
        $('#deselect-100').click(function() {
            publicMethods.multiSelect('deselect', demoValues);
            return false;
        });
        $('#refresh').on('click', function() {
            publicMethods.multiSelect('refresh');
            return false;
        });
        $('#add-option').on('click', function() {
            publicMethods.multiSelect('addOption', { value: 42, text: 'test 42', index: 0 });
            return false;
        });
        // Custom header/footer
        $('#ms-custom').multiSelect({
            selectableHeader: '<div class="bg-primary text-sm py-1 px-2">Selectable items</div>',
            selectionHeader: '<div class="bg-primary text-sm py-1 px-2">Selection items</div>',
            selectableFooter: '<div class="bg-primary text-sm py-1 px-2">Selectable footer</div>',
            selectionFooter: '<div class="bg-primary text-sm py-1 px-2">Selection footer</div>'
        });

        // UI SLider (noUiSlider)

        $('.ui-slider').each(function() {

            noUiSlider.create(this, {
                start: $(this).data('start'),
                connect: true,
                range: {
                    'min': 0,
                    'max': 100,
                }
            });
        });

        // Range selectable
        $('.ui-slider-range').each(function() {
            noUiSlider.create(this, {
                start: [25, 75],
                range: {
                    'min': 0,
                    'max': 100
                },
                connect: true
            });

        });

        // Live Values
        $('.ui-slider-values').each(function() {
            var slider = this;

            noUiSlider.create(slider, {
                start: [35, 75],
                connect: true,
                // direction: 'rtl',
                behaviour: 'tap-drag',
                range: {
                    'min': 0,
                    'max': 100
                }
            });

            slider.noUiSlider.on('slide', updateValues);
            updateValues();

            function updateValues() {
                var values = slider.noUiSlider.get();
                // Connecto to live values
                $('#ui-slider-value-lower').html(values[0]);
                $('#ui-slider-value-upper').html(values[1]);
            }
        });

        // Colorpicker

        $('#cp-demo-basic').colorpicker({
            customClass: 'colorpicker-2x',
            sliders: {
                saturation: {
                    maxLeft: 200,
                    maxTop: 200
                },
                hue: {
                    maxTop: 200
                },
                alpha: {
                    maxTop: 200
                }
            }
        });
        $('#cp-demo-component').colorpicker();
        $('#cp-demo-hex').colorpicker();

        $('#cp-demo-bootstrap').colorpicker({
            colorSelectors: {
                'default': '#777777',
                'primary': '#337ab7',
                'success': '#5cb85c',
                'info': '#5bc0de',
                'warning': '#f0ad4e',
                'danger': '#d9534f'
            }
        });

    }

})();
(function() {
    'use strict';

    $(formEditor);

    function formEditor() {

        // Summernote HTML editor
        $('.summernote').each(function() {
            $(this).summernote({
                height: 380
            });
        });

        $('.summernote-air').each(function() {
            $(this).summernote({
                airMode: true
            });
        });

        // Hide the initial popovers that display
        $('.note-popover').css({
            'display': 'none'
        });

    }

})();
(function() {
    'use strict';

    $(initTypeahead);

    function initTypeahead() {

        if (!$.fn.typeahead) return;

        // BASIC EXAMPLE
        // ----------------------

        var substringMatcher = function(strs) {
            return function findMatches(q, cb) {
                var matches, substrRegex;

                // an array that will be populated with substring matches
                matches = [];

                // regex used to determine if a string contains the substring `q`
                substrRegex = new RegExp(q, 'i');

                // iterate through the pool of strings and for any string that
                // contains the substring `q`, add it to the `matches` array
                $.each(strs, function(i, str) {
                    if (substrRegex.test(str)) {
                        matches.push(str);
                    }
                });

                cb(matches);
            };
        };

        var states = ['Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California',
            'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia', 'Hawaii',
            'Idaho', 'Illinois', 'Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana',
            'Maine', 'Maryland', 'Massachusetts', 'Michigan', 'Minnesota',
            'Mississippi', 'Missouri', 'Montana', 'Nebraska', 'Nevada', 'New Hampshire',
            'New Jersey', 'New Mexico', 'New York', 'North Carolina', 'North Dakota',
            'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania', 'Rhode Island',
            'South Carolina', 'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont',
            'Virginia', 'Washington', 'West Virginia', 'Wisconsin', 'Wyoming'
        ];

        $('#the-basics .typeahead').typeahead({
            hint: true,
            highlight: true,
            minLength: 1
        }, {
            name: 'states',
            source: substringMatcher(states)
        });


        // BLOODHOUND EXAMPLE
        // ----------------------
        // constructs the suggestion engine
        var bStates = new Bloodhound({
            datumTokenizer: Bloodhound.tokenizers.whitespace,
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            // `states` is an array of state names defined in "The Basics"
            local: states
        });

        $('#bloodhound .typeahead').typeahead({
            hint: true,
            highlight: true,
            minLength: 1
        }, {
            name: 'states',
            source: bStates
        });


        // PREFETCH EXAMPLE
        // ----------------------
        var countries = new Bloodhound({
            datumTokenizer: Bloodhound.tokenizers.whitespace,
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            // url points to a json file that contains an array of country names, see
            // https://github.com/twitter/typeahead.js/blob/gh-pages/data/countries.json
            prefetch: 'static/typeahead/countries.json'
        });

        // passing in `null` for the `options` arguments will result in the default
        // options being used
        $('#prefetch .typeahead').typeahead(null, {
            name: 'countries',
            source: countries
        });


        // DEFAULT SUGGESTION EXAMPLE
        // ----------------------------
        var nflTeams = new Bloodhound({
            datumTokenizer: Bloodhound.tokenizers.obj.whitespace('team'),
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            identify: function(obj) {
                return obj.team;
            },
            prefetch: 'static/typeahead/nfl.json'
        });

        function nflTeamsWithDefaults(q, sync) {
            if (q === '') {
                sync(nflTeams.get('Detroit Lions', 'Green Bay Packers', 'Chicago Bears'));
            } else {
                nflTeams.search(q, sync);
            }
        }

        $('#default-suggestions .typeahead').typeahead({
            minLength: 0,
            highlight: true
        }, {
            name: 'nfl-teams',
            display: 'team',
            source: nflTeamsWithDefaults
        });


        // MULTIPLE DATASET & CUSTOM TEMPLATE
        // ------------------------------------
        var nbaTeams = new Bloodhound({
            datumTokenizer: Bloodhound.tokenizers.obj.whitespace('team'),
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            prefetch: 'static/typeahead/nba.json'
        });

        var nhlTeams = new Bloodhound({
            datumTokenizer: Bloodhound.tokenizers.obj.whitespace('team'),
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            prefetch: 'static/typeahead/nhl.json'
        });

        $('#multiple-datasets .typeahead').typeahead({
            highlight: true
        }, {
            name: 'nba-teams',
            display: 'team',
            source: nbaTeams,
            templates: {
                header: '<h3 class="league-name">NBA Teams</h3>'
            }
        }, {
            name: 'nhl-teams',
            display: 'team',
            source: nhlTeams,
            templates: {
                header: '<h3 class="league-name">NHL Teams</h3>'
            }
        });

        // SCROLLABLE EXAMPLE
        // -------------------
        $('#scrollable-dropdown-menu .typeahead').typeahead(null, {
            name: 'countries',
            limit: 10,
            source: countries
        });

    }

})();
(function() {
    'use strict';

    $(formValidation);

    function formValidation() {

        if (!$.fn.validate) return;

        $('#form-register').validate({
            errorPlacement: errorPlacementInput,
            // Form rules
            rules: {
                email: {
                    required: true,
                    email: true
                },
                password1: {
                    required: true
                },
                confirm_match: {
                    required: true,
                    equalTo: '#id-password'
                }
            }
        });

        $('#form-example').validate({
            errorPlacement: errorPlacementInput,
            // Form rules
            rules: {
                sometext: {
                    required: true
                },
                email: {
                    required: true,
                    email: true
                },
                digits: {
                    required: true,
                    digits: true
                },
                url: {
                    required: true,
                    url: true
                },
                min: {
                    required: true,
                    min: 6
                },
                max: {
                    required: true,
                    max: 6
                },
                minlength: {
                    required: true,
                    minlength: 6
                },
                maxlength: {
                    required: true,
                    maxlength: 10
                },
                length: {
                    required: true,
                    range: [6, 10]
                },
                match1: {
                    required: true
                },
                confirm_match: {
                    required: true,
                    equalTo: '#id-source'
                }
            }
        });

    }

    // Necessary to place dyncamic error messages
    // without breaking the expected markup for custom input
    function errorPlacementInput(error, element) {
        if (element.is(':radio') || element.is(':checkbox')) {
            error.insertAfter(element.parent());
        } else {
            error.insertAfter(element);
        }
    }

})();
(function() {
    'use strict';

    $(formWizard);

    function formWizard() {

        if (!$.fn.steps) return;
        if (!$.fn.validate) return;

        var form = $('#example-form');

        form.validate({
            errorPlacement: errorPlacementInput,
            rules: {
                confirm: {
                    equalTo: '#password'
                }
            }
        });

        form.children('div').steps({
            headerTag: 'h4',
            bodyTag: 'fieldset',
            transitionEffect: 'slideLeft',
            onStepChanging: function(/*event, currentIndex, newIndex*/) {
                form.validate().settings.ignore = ':disabled,:hidden';
                return form.valid();
            },
            onFinishing: function(/*event, currentIndex*/) {
                form.validate().settings.ignore = ':disabled';
                return form.valid();
            },
            onFinished: function(/*event, currentIndex*/) {
                alert('Submitted!');

                // Submit form
                $(this).submit();
            }
        });

        // VERTICAL
        // -----------------------------------

        $('#example-vertical')
            .steps({
                headerTag: 'h4',
                bodyTag: 'section',
                transitionEffect: 'slideLeft',
                stepsOrientation: 'vertical'
            });


        // Necessary to place dyncamic error messages
        // without breaking the expected markup for custom input
        function errorPlacementInput(error, element) {
            if (element.is(':radio') || element.is(':checkbox')) {
                error.insertAfter(element.parent());
            } else {
                error.insertAfter(element);
            }
        }

    }

})();

(function() {
    'use strict';

    $(formXeditable);

    function formXeditable() {

        if( !$.fn.editableform ) return;

        // Font Awesome support
        $.fn.editableform.buttons =
            '<button type="submit" class="btn btn-primary editable-submit">' +
            '<i class="icon-fw ion-checkmark"></i>' +
            '</button>' +
            '<button type="button" class="btn btn-secondary editable-cancel">' +
            '<i class="icon-fw ion-close-round"></i>' +
            '</button>';

        //defaults
        // $.fn.editable.defaults.url = 'static/xeditable.res';

        //enable / disable
        var isDisabled = false;
        $('#enable').click(function() {
            isDisabled = !isDisabled;
            $('#x-user .editable').editable('option', 'disabled', isDisabled); // or .editable('toggleDisabled');
            $(this).text(isDisabled ? 'Set enable' : 'Set disable');
        });

        //editables
        $('#username').editable({
            type: 'text',
            pk: 1,
            name: 'username',
            title: 'Enter username',
            mode: 'inline'
        });

        $('#firstname').editable({
            validate: function(value) {
                if ($.trim(value) === '') return 'This field is required';
            },
            mode: 'inline'
        });

        $('#sex').editable({
            prepend: 'not selected',
            mode: 'inline',
            source: [{
                value: 1,
                text: 'Male'
            }, {
                value: 2,
                text: 'Female'
            }],
            display: function(value, sourceData) {
                var colors = {
                        '': 'gray',
                        1: 'green',
                        2: 'blue'
                    },
                    elem = $.grep(sourceData, function(o) {
                        return o.value === value;
                    });

                if (elem.length) {
                    $(this).text(elem[0].text).css('color', colors[value]);
                } else {
                    $(this).empty();
                }
            }
        });

        $('#status').editable({
            mode: 'inline'
        });

        $('#group').editable({
            showbuttons: false,
            mode: 'inline'
        });

        $('#dob').editable({
            mode: 'inline'
        });

        $('#event').editable({
            placement: 'right',
            combodate: {
                firstItem: 'name'
            },
            mode: 'inline'
        });

        $('#comments').editable({
            showbuttons: 'bottom',
            mode: 'inline'
        });

        $('#note').editable({
            mode: 'inline'
        });

        $('#pencil').click(function(e) {
            e.stopPropagation();
            e.preventDefault();
            $('#note').editable('toggle');
        });

        $('#x-user .editable').on('hidden', function(e, reason) {
            if (reason === 'save' || reason === 'nochange') {
                var $next = $(this).closest('tr').next().find('.editable');
                if ($('#autoopen').is(':checked')) {
                    setTimeout(function() {
                        $next.editable('show');
                    }, 300);
                } else {
                    $next.focus();
                }
            }
        });

        // TABLE
        // -----------------------------------

        $('#x-users a').editable({
            type: 'text',
            name: 'username',
            title: 'Enter username',
            mode: 'inline'
        });

    }

})();

(function() {
    'use strict';

    $(initKanban);

    function initKanban() {

        if (typeof dragula === 'undefined') return;

        // https://github.com/bevacqua/dragula/issues/426#issuecomment-260434933
        window.addEventListener('touchmove', function() {});

        calculateTotals(document.querySelector('#pending'));
        calculateTotals(document.querySelector('#in-progress'));
        calculateTotals(document.querySelector('#done'));

        dragula([
            document.querySelector('#pending'),
            document.querySelector('#in-progress'),
            document.querySelector('#done')
        ]).on('drop', function(el, target, source, sibling) {

            updateState(target);

            calculateTotals(target);
            calculateTotals(source);

        });

        function removeBackground(index, className) {
            return (className.match(/(^|\s)bg-gradient-\S+/g) || []).join(' ');
        }

        function removeBadge(index, className) {
            return (className.match(/(^|\s)badge-\S+/g) || []).join(' ');
        }

        function calculateTotals(target) {
            var budgets = $(target).find('.budget');
            var total = 0;
            budgets.each(function() { total += parseFloat($(this).text().replace('$', '').trim(), 2) })
            $(target).parents('.cardbox').find('.total').text('$ ' + total);
        }

        function updateState(target) {
            var topline = $(target).find('.top-line');
            var badge = $(target).find('.badge');
            if (target.id == 'pending') {
                topline.removeClass(removeBackground).addClass('bg-gradient-primary');
                badge.removeClass(removeBadge).addClass('badge-primary');
            }
            if (target.id == 'in-progress') {
                topline.removeClass(removeBackground).addClass('bg-gradient-info');
                badge.removeClass(removeBadge).addClass('badge-info');
            }
            if (target.id == 'done') {
                topline.removeClass(removeBackground).addClass('bg-gradient-success');
                badge.removeClass(removeBadge).addClass('badge-success');
            }
        }

    }

})();


(function(){
    'use strict';

    $(initMessages);

    function initMessages() {
        var msgList = $('.msg-display');

        msgList.each(function() {
            var msg = $(this);

            msg.on('click', function(e){
                // Ignores drodown click to avoid opening modal at the same time
                if( $(e.target).is('.dropdown') ||
                    $(e.target).parents('.dropdown').length > 0  ) {
                    return;
                }
                // Open modal
                $('.modal-message').modal();

            });

        });

        $('#compose').on('click', function(){
            $('.modal-compose').modal();
        });

    }

})();
(function() {
    'use strict';

    $(initTimeline);

    function initTimeline() {
        if (document.getElementById('map-tm')) {
            new GMaps({
                div: '#map-tm',
                lat: -12.043333,
                lng: -77.028333
            });
        }
    }

})();


(function() {
    'use strict';

    $(initSettings);

    function initSettings() {

        var body = $('body');
        // var sidebar = $('.layout-container > aside');
        // var header = $('.layout-container > header');
        // var brand = sidebar.find('.brand-header');
        // var content = $('.layout-container > main');

        // Handler for themes preview
        $('input[name="setting-theme"]:radio').change(function() {
            body.removeClass(themeClassname);
            body.addClass(this.value);
        });
            // Regular expression for the pattern bg-* to find the background class
            function themeClassname(index, css) {
                var cmatch = css.match(/(^|\s)theme-\S+/g);
                return (cmatch || []).join(' ');
            }

        $('#sidebar-cover').change(function() {
            body[this.checked ? 'addClass' : 'removeClass']('sidebar-cover');
        });

        $('#fixed-footer').change(function() {
            body[this.checked ? 'addClass' : 'removeClass']('footer-fixed');
        });

        var sidebarToolbar = $('.sidebar-toolbar');
        $('#sidebar-showtoolbar').change(function() {
            sidebarToolbar[this.checked ? 'show' : 'hide']();
        });

    }

})();

(function() {
    'use strict';

    $(sidebarNav);

    function sidebarNav() {

        var $sidebarNav = $('.sidebar-nav');
        var $sidebarContent = $('.sidebar-content');

        activate($sidebarNav);

        $sidebarNav.on('click', function(event) {
            var item = getItemElement(event);
            // check click is on a tag
            if (!item) return;

            var ele = $(item),
                liparent = ele.parent()[0];

            var lis = ele.parent().parent().children(); // markup: ul > li > a
            // remove .active from childs
            lis.find('li').removeClass('active');
            // remove .active from siblings ()
            $.each(lis, function(idx, li) {
                if (li !== liparent)
                    $(li).removeClass('active');
            });

            var next = ele.next();
            if (next.length && next[0].tagName === 'UL') {
                ele.parent().toggleClass('active');
                event.preventDefault();
            }
        });

        // find the a element in click context
        // doesn't check deeply, asumens two levels only
        function getItemElement(event) {
            var element = event.target,
                parent = element.parentNode;
            if (element.tagName.toLowerCase() === 'a') return element;
            if (parent.tagName.toLowerCase() === 'a') return parent;
            if (parent.parentNode.tagName.toLowerCase() === 'a') return parent.parentNode;
        }

        function activate(sidebar) {
            sidebar.find('a').each(function() {
                var $this = $(this),
                    href = $this.attr('href').replace('#', '');
                if (href !== '' && window.location.href.indexOf('/' + href) >= 0) {
                    var item = $this.parents('li').addClass('active');
                    // Animate scrolling to focus active item
                    $sidebarContent.animate({
                        scrollTop: $sidebarContent.scrollTop() + item.position().top - (window.innerHeight / 2)
                    }, 100);
                    return false; // exit foreach
                }
            });
        }

        var layoutContainer = $('.layout-container');
        var $body = $('body');
        // Handler to toggle sidebar visibility on mobile
        $('.sidebar-toggler').click(function(e) {
            e.preventDefault();
            layoutContainer.toggleClass('sidebar-visible');
            // toggle icon state
            $(this).parent().toggleClass('active');
        });
        // Close sidebar when click on backdrop
        $('.sidebar-layout-obfuscator').click(function(e) {
            e.preventDefault();
            $body.removeClass('sidebar-cover-visible'); // for use with cover mode
            layoutContainer.removeClass('sidebar-visible'); // for use on mobiles
            // restore icon
            $('.sidebar-toggler').parent().removeClass('active');
        });

        // escape key closes sidebar on desktops
        $(document).keyup(function(e) {
            if (e.keyCode === 27) {
                $body.removeClass('sidebar-cover-visible');
            }
        });

        // Handler to toggle sidebar visibility on desktop
        $('.covermode-toggler').click(function(e) {
            e.preventDefault();
            $body.addClass('sidebar-cover-visible');
        });

        $('.sidebar-close').click(function(e) {
            e.preventDefault();
            $body.removeClass('sidebar-cover-visible');
        });

        // remove desktop offcanvas when app changes to mobile
        // so when it returns, the sidebar is shown again
        window.addEventListener('resize', function() {
            if (window.innerWidth < 768) {
                $body.removeClass('sidebar-cover-visible');
            }
        });

    }

})();

(function() {
    'use strict';

    $(initFooTable);

    function initFooTable() {

        if (!$.fn.footable) return;

        $('.footable').footable();

    }

})();
(function() {
    'use strict';

    $(initTranslation);

    // Global configuration
    var preferredLang = 'en';
    var pathPrefix    = 'static/i18n'; // folder of json files
    var packName      = 'site';

    function initTranslation() {

        if (!$.fn.localize) return;

        // set initial options
        var opts = {
            language: preferredLang,
            pathPrefix: pathPrefix,
            callback: function(data, defaultCallback) {
                defaultCallback(data);
            }
        };

        // Set initial language
        setLanguage(opts);

        // Listen for changes
        $('.language-select').on('change', function() {

            var selectedLang = this.value;

            if (selectedLang && opts.language !== selectedLang) {

                opts.language = selectedLang;

                setLanguage(opts);

                activateDropdown($(this));
            }

        });
    }

    // Update translated text
    function setLanguage(options) {
        $('[data-localize]').localize(packName, options);
    }

    // Set the current clicked text as the active dropdown text
    function activateDropdown(elem) {
        var menu = elem.parents('.dropdown-menu');
        if (menu.length) {
            var toggle = menu.prev('button, a');
            toggle.text(elem.text());
        }
    }

})();



(function() {
    'use strict';

    $(userRecover);

    function userRecover() {

        if (!$.fn.validate) return;

        var $form = $('#user-recover');
        $form.validate({
            errorPlacement: errorPlacementInput,
            // Form rules
            rules: {
                accountName: {
                    required: true,
                    email: true
                }
            },
            submitHandler: function(/*form*/) {
                console.log('Form submitted!');
            }
        });
    }

    // Necessary to place dyncamic error messages
    // without breaking the expected markup for custom input
    function errorPlacementInput(error, element) {
        if ( element.is(':radio') || element.is(':checkbox')) {
            error.insertAfter(element.parent());
        }
        else if ( element.parent().is('.input-group') ) {
            error.insertAfter(element.parent());
        }
        else {
            error.insertAfter(element);
        }
    }

})();

(function() {
    'use strict';

    $(userSignin);

    function userSignin() {

        if (!$.fn.validate) return;

        var $form = $('#user-login');
        $form.validate({
            errorPlacement: errorPlacementInput,
            // Form rules
            rules: {
                accountName: {
                    required: true,
                    email: true
                },
                accountPassword: {
                    required: true
                }
            },
            submitHandler: function(/*form*/) {
                // form.submit();
                console.log('Form submitted!');
            }
        });
    }

    // Necessary to place dyncamic error messages
    // without breaking the expected markup for custom input
    function errorPlacementInput(error, element) {
        if ( element.is(':radio') || element.is(':checkbox')) {
            error.insertAfter(element.parent());
        }
        else {
            error.insertAfter(element);
        }
    }

})();

(function() {
    'use strict';

    $(userSignup);

    function userSignup() {

        if (!$.fn.validate) return;

        var $form = $('#user-signup');
        $form.validate({
            errorPlacement: errorPlacementInput,
            // Form rules
            rules: {
                accountName: {
                    required: true,
                    email: true
                },
                accountPassword: {
                    required: true
                },
                accountPasswordCheck: {
                    required: true,
                    equalTo: '#account-password'
                }
            },
            submitHandler: function( /*form*/ ) {
                // form.submit();
                console.log('Form submitted!');
                $('#form-ok').hide().removeClass('invisible').show(500);
            }
        });
    }


    // Necessary to place dyncamic error messages
    // without breaking the expected markup for custom input
    function errorPlacementInput(error, element) {
        if (element.is(':radio') || element.is(':checkbox')) {
            error.insertAfter(element.parent());
        } else {
            error.insertAfter(element);
        }
    }

})();
(function() {
    'use strict';

    $(initScreenfull);

    function initScreenfull() {
        var element = $('[data-toggle-fullscreen]');
        // Not supported under IE
        if (msie()) {
            element.hide();
        } else {
            element.on('click', function(e) {
                e.preventDefault();

                if (screenfull.enabled) {

                    screenfull.toggle();

                } else {
                    // Fullscreen not enabled
                }

            });
        }
    }

    function msie() {
        var ua = window.navigator.userAgent;
        var msie = ua.indexOf('MSIE ');
        return (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./));
    }

})();
(function() {
    'use strict';

    $(initWidgets);

    function initWidgets() {

        if(!$.fn.peity) return;

        $('.line1').peity('line', {
            fill: [Colors.byName('blue-200')],
            stroke: Colors.byName('blue-500'),
            strokeWidth: 2,
            height: 20,
            width: 60
        });

        $('.line2').peity('line', {
            fill: [Colors.byName('green-200')],
            stroke: Colors.byName('green-500'),
            strokeWidth: 2,
            height: 20,
            width: 60
        });

        $('.line3').peity('line', {
            fill: 'rgba(255,255,255,0.5)',
            stroke: '#fff',
            strokeWidth: 2,
            height: 20,
            width: 60
        });

        $('.bar1').peity('bar', {
            fill: [Colors.byName('deepPurple-500')],
            height: 30,
            width: 60
        });

        $('.bar2').peity('bar', {
            fill: [Colors.byName('pink-500')],
            height: 30,
            width: 60
        });

    }


})();