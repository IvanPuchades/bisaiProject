'use strict';

describe('Controller Tests', function() {

    describe('ValoracionEquipo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockValoracionEquipo, MockEquipo, MockJugador;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockValoracionEquipo = jasmine.createSpy('MockValoracionEquipo');
            MockEquipo = jasmine.createSpy('MockEquipo');
            MockJugador = jasmine.createSpy('MockJugador');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ValoracionEquipo': MockValoracionEquipo,
                'Equipo': MockEquipo,
                'Jugador': MockJugador
            };
            createController = function() {
                $injector.get('$controller')("ValoracionEquipoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bisaiApp:valoracionEquipoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
