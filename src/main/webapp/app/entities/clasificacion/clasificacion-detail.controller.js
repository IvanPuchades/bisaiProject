(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('ClasificacionDetailController', ClasificacionDetailController);

    ClasificacionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Clasificacion', 'Torneo', 'Equipo'];

    function ClasificacionDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Clasificacion, Torneo, Equipo) {
        var vm = this;

        vm.clasificacion = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('bisaiApp:clasificacionUpdate', function(event, result) {
            vm.clasificacion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
