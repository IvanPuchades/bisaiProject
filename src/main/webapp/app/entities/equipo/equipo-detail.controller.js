(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('EquipoDetailController', EquipoDetailController);

    EquipoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Equipo', 'Jugador', 'Clasificacion', 'ValoracionEquipo', 'AdministradorEquipo', 'Partida', 'Torneo'];

    function EquipoDetailController($scope, $rootScope, $stateParams, previousState, entity, Equipo, Jugador, Clasificacion, ValoracionEquipo, AdministradorEquipo, Partida, Torneo) {
        var vm = this;

        vm.equipo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bisaiApp:equipoUpdate', function(event, result) {
            vm.equipo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
