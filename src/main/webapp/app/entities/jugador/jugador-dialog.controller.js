(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('JugadorDialogController', JugadorDialogController);

    JugadorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Jugador', 'User', 'ValoracionJugador', 'ValoracionEquipo', 'AdministradorEquipo', 'Porra', 'Equipo', 'Torneo'];

    function JugadorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Jugador, User, ValoracionJugador, ValoracionEquipo, AdministradorEquipo, Porra, Equipo, Torneo) {
        var vm = this;

        vm.jugador = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.valoracionjugadors = ValoracionJugador.query();
        vm.valoracionequipos = ValoracionEquipo.query();
        vm.administradorequipos = AdministradorEquipo.query();
        vm.porras = Porra.query();
        vm.equipos = Equipo.query();
        vm.torneos = Torneo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.jugador.id !== null) {
                Jugador.update(vm.jugador, onSaveSuccess, onSaveError);
            } else {
                Jugador.save(vm.jugador, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bisaiApp:jugadorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
