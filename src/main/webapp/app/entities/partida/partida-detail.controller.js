(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('PartidaDetailController', PartidaDetailController);

    PartidaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Partida', 'Equipo', 'Torneo', 'Porra'];

    function PartidaDetailController($scope, $rootScope, $stateParams, previousState, entity, Partida, Equipo, Torneo, Porra) {
        var vm = this;

        vm.partida = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bisaiApp:partidaUpdate', function(event, result) {
            vm.partida = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
