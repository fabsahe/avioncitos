#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define N 1
#define TAM_NOM 50
#define TAM_COD 20
#define TAM_CAT 20
#define TAM_NOR 6


typedef struct{
	char nombre[TAM_NOM];
	char codigo[TAM_COD];
	char categoria[TAM_CAT];
	char anti[3];
}EMPLEADO;

typedef struct{
    int sueldo;//SUELDO BASE POR DIA
    char entra[TAM_NOM];//ENTRADA DE EMPLEADO
    char sali[TAM_NOM];//SALIDA DE EMPLEADO
    char fal[TAM_NOM];//FALTAS
    char retar[TAM_NOM];//RETARDO
    char permis[TAM_NOM];//PERMISOS
}ADMINISTRACION;

typedef struct{
    ADMINISTRACION extra;//bonificación
    ADMINISTRACION descu;//descuento
    ADMINISTRACION total;//PAGO TOTAL A LA SEMANA
}PAGO;

typedef struct{
	PAGO irs;
	PAGO imss;
}IMPUESTO;


void agregaEmpleado( EMPLEADO * );
void guardaEmpleado( EMPLEADO * );
int leeEmpleados( EMPLEADO * );
void imprimeEmpleado(EMPLEADO *, int);
void modificaCategoria(EMPLEADO *, int);
void modificaAntiguedad(EMPLEADO *, int);
int menu();
int leeAsistencia (ADMINISTRACION *);
void imprimeAsistencia(ADMINISTRACION *, int);
void guardaEmpleado (EMPLEADO *, int );
void pagoNomina(EMPLEADO *, int , ADMINISTRACION *);

void imprimeNombres( EMPLEADO * empleados, int tam ) {
    int j=0;
    for(j=0;j<tam;j++) {
        printf( "%d %s\n", j+1, empleados[j].nombre );
    }
    printf("\n");
}

int main(){
    int x, n, y, m;

    char salto;
    char opcion[3];
    EMPLEADO empleado[1];
    EMPLEADO empleados[100];
    ADMINISTRACION administracion[100];
    x = menu();

    switch (x){
        case 1 :  n=leeEmpleados(empleados);
                  imprimeEmpleado(empleados,n);
                  printf("Desea modificar algun empleado? s/n\n");
                  scanf("%s", opcion);
                  getchar();

                  if(strcmp(opcion, "S") || strcmp(opcion, "s")){
                    int num;
                    printf("Que numero de empleado desea modificar?");
                    scanf("%d", &num);
                    //getchar();

                    printf("¿Desea cambiar la categoria? s/n\n");
                    scanf("%s", opcion);
                    getchar();
                    if (strcmp(opcion, "S") || strcmp(opcion, "s")){
                        printf("%s\n", empleados[num-1].nombre);
                        modificaCategoria(empleados, num-1);
                        printf("%s", empleados[num-1].categoria);
                    }
                    else {
                        printf("¿Desea cambiar la antiguedad s/n\n");
                        scanf("%s", opcion);
                        if (strcmp(opcion, "S") || strcmp(opcion, "s")){
                            printf("%s\n", empleados[num-1].nombre);
                            modificaAntiguedad(empleados, num-1);
                            printf("%s", empleados[num-1].anti);
                        }else{
                            break;
                        }

                    }

                  }else{
                      break;

                  }
                  imprimeEmpleado(empleados, n);
                  guardaEmpleado(empleados, n);


            break;
        case 2 : agregaEmpleado(empleado);
                 guardaEmpleado(empleado);
            break;

        case 3 :
                 n = leeEmpleados(empleado);
                 m = leeAsistencia(administracion);

                pagoNomina(empleado, n, administracion);
            break;

        case 4 :
            break;

        case 5 :
                n=leeAsistencia(administracion);
                imprimeAsistencia(administracion, n);
            break;

        case 6 :

            break;


    }


   return 0;
}

int menu (){
    int x;
    printf("BIENVENIDO A LA PLATAFORMA \n");
	printf("\n");
	do{
	printf("1. Modificar Empleado\n");
	printf("2.Agregar Empleado\n");
	printf("3.Pago de nomina\n");
	printf("4.Pago de impuestos isr y imss\n");
	printf("5.Reporte de asistencias\n");
	printf("6.Administracion de asistencias\n");
	printf("7.Salida\n");
	scanf("%d",&x);
	}while(x!=1 && x!=2 && x!=3 && x!=4 && x!=5 && x!=6);
	return x;

}

void agregaEmpleado(EMPLEADO *listaEmpleados){
      getchar();
      printf("\tNombre: ");
      scanf("%s", listaEmpleados[0].nombre );
      //fgets(listaEmpleados[0].nombre, TAM_NOM, stdin );
      printf("\tCodigo: ");
      scanf("%s", listaEmpleados[0].codigo);
      //fgets(listaEmpleados[0].codigo, TAM_COD, stdin );
      printf("\tCategoria: ");
      scanf("%s", listaEmpleados[0].categoria)
      //fgets(listaEmpleados[0].categoria, TAM_CAT, stdin );
      printf("\tAntiguedad: ");
      scanf("%s", listaEmpleados[0].anti);
      //fgets(listaEmpleados[0].anti, 3, stdin );
      //putchar('\n');

   }

void guardaEmpleado(EMPLEADO *listaEmpleados){
   FILE *archivoPtr;

   printf("Guardando empleado en el archivo...\n");
   if((archivoPtr = fopen("empleados.txt", "a")) == NULL)
      printf("El archivo no pudo ser abierto...\n");
   else{
            printf("HEETETETE %s", listaEmpleados[0].nombre );

         fprintf(archivoPtr, "%s", listaEmpleados[0].nombre);
         fprintf(archivoPtr, "%s", listaEmpleados[0].codigo);
         fprintf(archivoPtr, "%s", listaEmpleados[0].categoria);
         fprintf(archivoPtr, "%s", listaEmpleados[0].anti);

      fclose(archivoPtr);
      printf("Listo!\n");
   }

   if((archivoPtr = fopen("administracion.txt", "a"))== NULL){
        printf("El archivo no pudo ser abierto...\n");
   }else{
        printf("Escribiendo...\n");
        fprintf(archivoPtr, "0\n");
        fprintf(archivoPtr, "00:00\n");
        fprintf(archivoPtr, "00:00\n");
        fprintf(archivoPtr, "0\n");
        fprintf(archivoPtr, "0\n");
        fprintf(archivoPtr, "0\n");

    fclose(archivoPtr);
   }
}


int leeEmpleados (EMPLEADO *empleado){
        FILE *archivoPTR;
        int i=0;

        if((archivoPTR = fopen("empleados.txt", "r"))==NULL)
            printf("El archivo no pudo ser abierto.\n");
        else{
            for( ; fgets(empleado[i].nombre, TAM_NOM, archivoPTR) !=NULL; i++){
                    //printf("VERIFICANDO NOMBRE: %s\n", empleado[i].nombre );
                fgets(empleado[i].codigo, TAM_COD, archivoPTR);
                fgets(empleado[i].categoria, TAM_CAT, archivoPTR);
                fgets(empleado[i].anti, 3, archivoPTR);
            }
            fclose(archivoPTR);
        }
        return i;
}

void imprimeEmpleado(EMPLEADO *empleado, int n){
    int i;

    printf("Lista de Empleados:\n");
    for(i=0; i<n; i++){
        printf("%d. %s", i+1, empleado[i].nombre);
        printf("codigo.%s\n", empleado[i].codigo);
        printf("categoria.%s\n", empleado[i].categoria);
        printf("antiguedad.%s\n", empleado[i].anti);
    }
}

void modificaCategoria(EMPLEADO *empleado, int n){

    printf("Categoria anterior: %s\n", empleado[n].categoria);
    printf("Categoria nueva: \n ");
    scanf("%s", empleado[n].categoria);
}

void modificaAntiguedad (EMPLEADO *empleado, int n) {

    printf("Antiguedad anterior: %s\n", empleado[n].anti);
    printf("Antiguedad actual: \n ");
    scanf("%s", empleado[n].anti);
}

void guardaEmpleado (EMPLEADO *empleados, int n) {
    FILE *archivoPtr;
    int i=0;

    if((archivoPtr = fopen("empleados.txt", "w+")) == NULL){
        printf("El archivo no pudo ser abierto");
    }else{
        for (i=0; i<n; i++){
            fprintf(archivoPtr, "%s\n", empleados[i].nombre);
            fprintf(archivoPtr, "%s\n", empleados[i].codigo);
            fprintf(archivoPtr, "%s\n", empleados[i].categoria);
            fprintf(archivoPtr, "%s\n", empleados[i].anti);
        }

    }

}


int leeAsistencia (ADMINISTRACION *administracion){
    FILE *archivoPtr;
    int i=0;

    if((archivoPtr = fopen("administracion.txt", "r")) == NULL){
        printf("El archivo no pudo ser abierto");
    }else{
        for(;fscanf(archivoPtr, "%d", &administracion[i].sueldo )!=EOF; i++){

            fgets(administracion[i].entra,TAM_NOR, archivoPtr);
            fgets(administracion[i].sali,TAM_NOR, archivoPtr);
            fgets(administracion[i].fal,TAM_NOR, archivoPtr);
            fgets(administracion[i].retar,TAM_NOR, archivoPtr);
            fgets(administracion[i].permis,TAM_NOR, archivoPtr);
        }
            fclose(archivoPtr);
    }
        return i;
}

void imprimeAsistencia(ADMINISTRACION *administracion, int n){
    int i;

    printf("Lista de Asistencia:\n");
    for(i=0; i<n; i++){
        printf("Sueldo. %d", administracion[i].sueldo);
        printf("Hora de entrada.%s\n", administracion[i].entra);
        printf("Hora de salida.%s\n", administracion[i].sali);
        printf("Faltas.%s\n", administracion[i].fal);
        printf("Retardos.%s\n", administracion[i].retar);
        printf("Permisos.%s\n", administracion[i].permis);
    }
}

void pagoNomina(EMPLEADO * empleado, int n, ADMINISTRACION * administracion){
    int num, kk;
    int antiguedad,sueldo;
    FILE *archivoPtr;
    int i;
    printf("no emp: %d\n", n );
    //imprimeNombres( empleado, n );
    for(kk=0;kk<n;kk++) {
        printf(" %d %s\n", kk, empleado[kk].nombre );
    }
    printf("¿Que empleado desea editar?");
    scanf("%d", &num);

    antiguedad = atoi(empleado[num-1].anti);
    antiguedad = antiguedad/5 * 100;
    sueldo = 1500 + antiguedad;

    administracion[num-1].sueldo = sueldo;

    if((archivoPtr = fopen("administracion.txt", "w+")) == NULL){
        printf("El archivo no pudo ser abierto");
    }else{
        for (i=0; i<n; i++){
            fprintf(archivoPtr, "%d\n", administracion[i].sueldo);
            printf("entrada: %s\n", administracion[i].entra);
            fprintf(archivoPtr, "%s\n", administracion[i].entra);
            fprintf(archivoPtr, "%s\n", administracion[i].sali);
            fprintf(archivoPtr, "%s\n", administracion[i].fal);
            fprintf(archivoPtr, "%s\n", administracion[i].retar);
            fprintf(archivoPtr, "%s\n", administracion[i].permis);
        }
    }
    fclose(archivoPtr);
}

