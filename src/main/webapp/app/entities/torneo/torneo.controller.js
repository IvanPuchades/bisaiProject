(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('TorneoController', TorneoController);

    TorneoController.$inject = ['$scope', '$state', 'Torneo'];

    function TorneoController ($scope, $state, Torneo) {
        var vm = this;

        vm.torneos = [];

        loadAll();

        function loadAll() {
            Torneo.query(function(result) {
                vm.torneos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
