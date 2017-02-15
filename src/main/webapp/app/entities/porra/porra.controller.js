(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('PorraController', PorraController);

    PorraController.$inject = ['$scope', '$state', 'Porra'];

    function PorraController ($scope, $state, Porra) {
        var vm = this;

        vm.porras = [];

        loadAll();

        function loadAll() {
            Porra.query(function(result) {
                vm.porras = result;
                vm.searchQuery = null;
            });
        }
    }
})();
