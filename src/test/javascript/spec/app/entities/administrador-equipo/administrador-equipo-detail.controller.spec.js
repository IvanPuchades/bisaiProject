'use strict';

describe('Controller Tests', function() {

    describe('AdministradorEquipo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAdministradorEquipo, MockEquipo, MockJugador;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAdministradorEquipo = jasmine.createSpy('MockAdministradorEquipo');
            MockEquipo = jasmine.createSpy('MockEquipo');
            MockJugador = jasmine.createSpy('MockJugador');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'AdministradorEquipo': MockAdministradorEquipo,
                'Equipo': MockEquipo,
                'Jugador': MockJugador
            };
            createController = function() {
                $injector.get('$controller')("AdministradorEquipoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bisaiApp:administradorEquipoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
