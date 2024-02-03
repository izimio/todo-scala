import shell._

@main def hello(): Unit =

  val Shell = new Shell()
  Shell.run()
  Shell.save() 
