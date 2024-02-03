package shell

import todo._


class Shell() {
    val todoList = new TodoList

    def help(): Unit = {
        print("Available commands: \n" +
            " help: show this help message\n" +
            " exit: exit the program\n" +
            " add: add a new todo, <title> <description>\n" +
            " remove: remove a todo, <id>\n" +
            " list: list all todos\n" +
            " tick: mark a todo as done or not done, <id>\n"
        )
    }
    def run(): Unit = {
        
        while (true) {
            print("> ")
            val input = scala.io.StdIn.readLine()
            val command = input.split(" ")(0)
            val args = input.split(" ").tail
            command match { 
                case "exit" => println("Goodbye!"); return
                case "help" => help()
                case "add" => {
                     args.length match {
                        case 0 => print("Please provide a title and a description")
                        case 1 => print("Please provide a description")
                        case _ => {
                            val title = args(0)
                            val description = args(1)
                            todoList.add(title, description)
                        }
                     }
                }
                case "remove" => {
                    args.length match {
                        case nb if nb < 0 => print("Please provide a valid todo id")
                        case _ => {
                            val id = args(0).toInt
                            todoList.remove(id)
                        }
                    }
                }
                case "list" => todoList.enumerate()
                case "tick" => {
                    args.length match {
                        case nb if nb < 0 => print("Please provide a valid todo id")
                        case _ => {
                            val id = args(0).toInt
                            todoList.tick(id)
                        }
                    }
                }
                case "" =>;
                case _ => print("Unknown command")
            }
            println()
        }
    }
           def save(): Unit = {
            import java.io._
            val file = new File("todos.txt")
            val bw = new BufferedWriter(new FileWriter(file))
            todoList.list().reverse.foreach(todo => {
                bw.write(s"${todo.id},${todo.title},${todo.description},${todo.isDone}\n")
            })
            bw.close()
        }

        def load(): Unit = {
            println(" === Loading todos from file... === ")
            import scala.io.Source
            if (!new java.io.File("todos.txt").exists) return
            val file = Source.fromFile("todos.txt")
            val lines = file.getLines().toList
            lines.foreach(line => {
                val Array(id, title, description, isDone) = line.split(",")
                todoList.add(title, description)
                if (isDone.toBoolean) {
                    todoList.tick(id.toInt)
                }
            })
            println(" === Todos loaded === ")
        }
        load()
  
}
