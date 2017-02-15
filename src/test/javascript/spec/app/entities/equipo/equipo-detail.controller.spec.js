'use strict';

describe('Controller Tests', function() {

    describe('Equipo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEquipo, MockJugador, MockClasificacion, MockValoracionEquipo, MockAdministradorEquipo, MockPartida, MockTorneo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEquipo = jasmine.createSpy('MockEquipo');
            MockJugador = jasmine.createSpy('MockJugador');
            MockClasificacion = jasmine.createSpy('MockClasificacion');
            MockValoracionEquipo = jasmine.createSpy('MockValoracionEquipo');
            MockAdministradorEquipo = jasmine.createSpy('MockAdministradorEquipo');
            MockPartida = jasmine.createSpy('MockPartida');
            MockTorneo = jasmine.createSpy('MockTorneo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Equipo': MockEquipo,
                'Jugador': MockJugador,
                'Clasificacion': MockClasificacion,
                'ValoracionEquipo': MockValoracionEquipo,
                'AdministradorEquipo': MockAdministradorEquipo,
                'Partida': MockPartida,
                'Torneo': MockTorneo
            };
            createController = function() {
                $injector.get('$controller')("EquipoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bisaiApp:equipoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
