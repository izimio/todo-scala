package todo

class TodoList {
  var id = 0
  private var todos = List.empty[Todo]

  def add(title: String, description: String): Unit = {
    var todo = Todo(
      id = id,
      title = title,
      description = description,
      isDone = false
    )
    id += 1
    todos = todo :: todos 
    println(s"Todo added with id ${todo.id}")
  }

  def remove(id: Int): Unit = {
    if (todos.exists(_.id == id)) {
    todos = todos.filterNot(_.id == id)
    println(s"Todo with id $id removed")
    } else {
      println(s"Todo with id $id not found")
    }

  }

  def enumerate(): Unit = {
    todos.foreach(
      todo => println(s"${todo.id}: ${todo.title} - ${todo.description} - ${if (todo.isDone) "Done" else "Not done"}")
    )
  }

  def tick(id: Int): Unit = {
    if (!todos.exists(_.id == id)) {
      println(s"Todo with id $id not found")
      return
    }  
    todos = todos.map(todo =>
      if (todo.id == id) todo.copy(isDone = !todo.isDone)
      else todo
    )
    println(s"Todo with id $id marked as ${if (todos.find(_.id == id).get.isDone) "done" else "not done"}")
  }

  def list(): List[Todo] = todos
}

case class Todo(
  id: Int,
  title: String,
  description: String,
  isDone: Boolean
)
