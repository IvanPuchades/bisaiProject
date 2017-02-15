'use strict';

describe('Controller Tests', function() {

    describe('Partida Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPartida, MockEquipo, MockTorneo, MockPorra;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPartida = jasmine.createSpy('MockPartida');
            MockEquipo = jasmine.createSpy('MockEquipo');
            MockTorneo = jasmine.createSpy('MockTorneo');
            MockPorra = jasmine.createSpy('MockPorra');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Partida': MockPartida,
                'Equipo': MockEquipo,
                'Torneo': MockTorneo,
                'Porra': MockPorra
            };
            createController = function() {
                $injector.get('$controller')("PartidaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bisaiApp:partidaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
