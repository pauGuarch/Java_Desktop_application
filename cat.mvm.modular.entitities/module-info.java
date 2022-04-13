module cat.mvm.modular.entities {
    requires java.base;  // diu que necessita aquest package per funcionar
    requires cat.mvm.modular.utils;
    exports cat.mvm.modular.entities;  //aqui diem que altres podran accedir a aquest modul/package.
}