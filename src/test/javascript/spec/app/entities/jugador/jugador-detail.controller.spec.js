'use strict';

describe('Controller Tests', function() {

    describe('Jugador Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockJugador, MockUser, MockValoracionJugador, MockValoracionEquipo, MockAdministradorEquipo, MockPorra, MockEquipo, MockTorneo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockJugador = jasmine.createSpy('MockJugador');
            MockUser = jasmine.createSpy('MockUser');
            MockValoracionJugador = jasmine.createSpy('MockValoracionJugador');
            MockValoracionEquipo = jasmine.createSpy('MockValoracionEquipo');
            MockAdministradorEquipo = jasmine.createSpy('MockAdministradorEquipo');
            MockPorra = jasmine.createSpy('MockPorra');
            MockEquipo = jasmine.createSpy('MockEquipo');
            MockTorneo = jasmine.createSpy('MockTorneo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Jugador': MockJugador,
                'User': MockUser,
                'ValoracionJugador': MockValoracionJugador,
                'ValoracionEquipo': MockValoracionEquipo,
                'AdministradorEquipo': MockAdministradorEquipo,
                'Porra': MockPorra,
                'Equipo': MockEquipo,
                'Torneo': MockTorneo
            };
            createController = function() {
                $injector.get('$controller')("JugadorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bisaiApp:jugadorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
