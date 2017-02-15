(function() {
    'use strict';

    angular
        .module('bisaiApp')
        .controller('AdministradorEquipoDialogController', AdministradorEquipoDialogController);

    AdministradorEquipoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AdministradorEquipo', 'Equipo', 'Jugador'];

    function AdministradorEquipoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AdministradorEquipo, Equipo, Jugador) {
        var vm = this;

        vm.administradorEquipo = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.equipos = Equipo.query();
        vm.jugadors = Jugador.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.administradorEquipo.id !== null) {
                AdministradorEquipo.update(vm.administradorEquipo, onSaveSuccess, onSaveError);
            } else {
                AdministradorEquipo.save(vm.administradorEquipo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bisaiApp:administradorEquipoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.hora = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
