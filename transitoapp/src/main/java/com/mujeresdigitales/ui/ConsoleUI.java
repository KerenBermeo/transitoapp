package com.mujeresdigitales.ui;

import java.util.List;
import java.util.Scanner;
import com.mujeresdigitales.service.*;
import com.mujeresdigitales.model.Multa;
import com.mujeresdigitales.model.Persona;


public class ConsoleUI {
    private UserService userService = new UserService();
    private PersonaService personaService = new PersonaService();
    private MultaService multaService = new MultaService();

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al Sistema de Tránsito");

        while (true) {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Logearse");
            System.out.println("2. Registrarse");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    if (login(scanner)) {
                        mostrarMenu(scanner);
                    }
                    break;
                case 2:
                    registrarUsuario(scanner);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    return; // Termina el programa
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private boolean login(Scanner scanner) {
        System.out.print("Ingrese su usuario: ");
        String login = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String password = scanner.nextLine();

        return userService.login(login, password);
    }

    private void mostrarMenu(Scanner scanner) {
        int opcion;
        do {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Ingresar persona");
            System.out.println("2. Ver todas las personas");
            System.out.println("3. Crear multa");
            System.out.println("4. Ver todas las multas");
            System.out.println("5. Actualizar multa");
            System.out.println("6. Eliminar multa");
            System.out.println("7. Actualizar cuenta");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    createPersona(scanner);
                    break;
                case 2:
                    verTodasLasPersonas(scanner);
                    break;
                case 3:
                    crearMulta(scanner);
                    break;
                case 4:
                    verTodasLasMultas(scanner);
                    break;
                case 5:
                    actualizarMulta(scanner);
                    break;
                case 6:
                    eliminarMulta(scanner);
                    break;
                case 7:
                    actualizarUsuario(scanner);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    private void createPersona(Scanner scanner) {
        System.out.print("Ingrese nombre de persona: ");
        String userName = scanner.nextLine();

        System.out.print("Ingrese correo de persona: ");
        String userCorreo = scanner.nextLine();

        Persona persona = new Persona();
        persona.setPersonaNombre(userName);
        persona.setPersonaCorreo(userCorreo);

        personaService.createPersona(persona);
    }

    private void verTodasLasPersonas(Scanner scanner) {
        List<Persona> personas = personaService.getAllPersonas();
        if (personas != null && !personas.isEmpty()) {
            for (Persona persona : personas) {
                System.out.println("ID: " + persona.getPersonaId() + " - Nombre: " + persona.getPersonaNombre() + " - Correo: " + persona.getPersonaCorreo());
            }
        } else {
            System.out.println("No se encontraron personas.");
        }
    }

    private void crearMulta(Scanner scanner) {
        System.out.print("Ingrese ID de la persona a quien se le asignará la multa: ");
        int personaId = scanner.nextInt();

        System.out.print("Ingrese los días de mora: ");
        int diasMora = scanner.nextInt();

        System.out.print("Ingrese el valor de la multa: ");
        double valorMulta = scanner.nextDouble();
        scanner.nextLine(); // Consumir el salto de línea

        // Validar existencia de la persona
        Persona persona = personaService.getPersonaById(personaId);
        if (persona == null) {
            System.out.println("La persona con ID " + personaId + " no existe.");
            return;
        }

        Multa multa = new Multa();
        multa.setPersonaId(personaId);
        multa.setMultaDiasMora(diasMora);
        multa.setMultaValorMulta(valorMulta);

        multaService.calcularYGuardarMulta(multa);
        System.out.println("Multa creada con éxito.");
    }

    private void verTodasLasMultas(Scanner scanner) {
        List<Multa> multas = multaService.obtenerTodasLasMultas();
        if (multas != null && !multas.isEmpty()) {
            for (Multa multa : multas) {
                System.out.println("ID: " + multa.getMultaId() + " - Valor a pagar: " + multa.getMultaValorPagar() + " - Persona ID: " + multa.getPersonaId());
            }
        } else {
            System.out.println("No se encontraron multas.");
        }
    }

    private void actualizarMulta(Scanner scanner) {
        System.out.print("Ingrese ID de la multa a actualizar: ");
        int multaId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        System.out.print("Ingrese nuevo valor de la multa: ");
        double valorMulta = scanner.nextDouble();
        scanner.nextLine(); // Consumir el salto de línea

        Multa multa = multaService.obtenerMultaPorId(multaId);
        if (multa != null) {
            multa.setMultaValorMulta(valorMulta);
            multaService.actualizarMulta(multa);
            System.out.println("Multa actualizada con éxito.");
        } else {
            System.out.println("Multa no encontrada.");
        }
    }

    private void eliminarMulta(Scanner scanner) {
        System.out.print("Ingrese ID de la multa a eliminar: ");
        int multaId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        multaService.eliminarMulta(multaId);
        System.out.println("Multa eliminada con éxito.");
    }

    private void registrarUsuario(Scanner scanner) {
        System.out.print("Ingrese nombre del usuario: ");
        String userName = scanner.nextLine();

        System.out.print("Ingrese login del usuario: ");
        String userLogin = scanner.nextLine();

        System.out.print("Ingrese contraseña del usuario: ");
        String userPassword = scanner.nextLine();

        userService.registerUser(userName, userLogin, userPassword);
    }

    private void actualizarUsuario(Scanner scanner) {
        System.out.print("Ingrese ID del usuario a actualizar: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        System.out.print("Ingrese nuevo nombre del usuario (dejar en blanco para no cambiar): ");
        String userName = scanner.nextLine();

        System.out.print("Ingrese nuevo login del usuario (dejar en blanco para no cambiar): ");
        String userLogin = scanner.nextLine();

        System.out.print("Ingrese nueva contraseña del usuario (dejar en blanco para no cambiar): ");
        String userPassword = scanner.nextLine();

        userService.updateUser(userId, userName.isEmpty() ? null : userName,
                userLogin.isEmpty() ? null : userLogin, userPassword.isEmpty() ? null : userPassword);
    }
}