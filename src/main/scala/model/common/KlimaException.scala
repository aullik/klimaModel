package model.common

import java.util.{Locale, ResourceBundle}

sealed abstract class KlimaException(name: String) extends RuntimeException {

  final def getMessage(locale: Locale): String = {
    ResourceBundle.getBundle("ExceptionMessages", locale).getString(name)
  }

  override final def getMessage: String = {
    getMessage(Locale.getDefault)
  }

}

object KlimaException {

  case class IllegalEmailException() extends KlimaException("IllegalEmail")

  case class IllegalNameException() extends KlimaException("IllegalName")

  case class IllegalPasswordException() extends KlimaException("IllegalPassword")

  case class EmailInUseException() extends KlimaException("EmailInUse")

  case class NameInUseException() extends KlimaException("NameInUse")

  case class PasswordAlreadySetException() extends KlimaException("PasswordAlreadySet")

  case class PasswordNotSetException() extends KlimaException("PasswordNotSet")

  case class PasswordsDoNotMatchException() extends KlimaException("PasswordsDoNotMatch")

  case class WrongPasswordException() extends KlimaException("WrongPassword")

  case class InvalidAuthenticationException() extends KlimaException("InvalidAuthentication")

  case class NoAuthorizationException() extends KlimaException("NoAuthorization")

  case class EntityNotFoundException() extends KlimaException("EntityNotFound")

  case class DuplicateKeyException() extends KlimaException("DuplicateKey")

  case class AlreadyATeamMemberException() extends KlimaException("AlreadyATeamMember")

}
