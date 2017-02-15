(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('AdministradorEquipoDetailController', AdministradorEquipoDetailController);

    AdministradorEquipoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AdministradorEquipo', 'Equipo', 'Jugador'];

    function AdministradorEquipoDetailController($scope, $rootScope, $stateParams, previousState, entity, AdministradorEquipo, Equipo, Jugador) {
        var vm = this;

        vm.administradorEquipo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bisaiApp:administradorEquipoUpdate', function(event, result) {
            vm.administradorEquipo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
