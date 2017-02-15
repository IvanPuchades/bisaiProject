(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('PartidaController', PartidaController);

    PartidaController.$inject = ['$scope', '$state', 'Partida'];

    function PartidaController ($scope, $state, Partida) {
        var vm = this;

        vm.partidas = [];

        loadAll();

        function loadAll() {
            Partida.query(function(result) {
                vm.partidas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
