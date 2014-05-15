class User < ActiveRecord::Base
  has_secure_password
  
  attr_accessible :email, :password, :password_confirmation, :usertype
  
  validates_uniqueness_of :email
  
  def to_param
    email
  end
end
