(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('ValoracionEquipoDetailController', ValoracionEquipoDetailController);

    ValoracionEquipoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ValoracionEquipo', 'Equipo', 'Jugador'];

    function ValoracionEquipoDetailController($scope, $rootScope, $stateParams, previousState, entity, ValoracionEquipo, Equipo, Jugador) {
        var vm = this;

        vm.valoracionEquipo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bisaiApp:valoracionEquipoUpdate', function(event, result) {
            vm.valoracionEquipo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
