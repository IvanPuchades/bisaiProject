'use strict';

describe('Controller Tests', function() {

    describe('Clasificacion Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockClasificacion, MockTorneo, MockEquipo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockClasificacion = jasmine.createSpy('MockClasificacion');
            MockTorneo = jasmine.createSpy('MockTorneo');
            MockEquipo = jasmine.createSpy('MockEquipo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Clasificacion': MockClasificacion,
                'Torneo': MockTorneo,
                'Equipo': MockEquipo
            };
            createController = function() {
                $injector.get('$controller')("ClasificacionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bisaiApp:clasificacionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
