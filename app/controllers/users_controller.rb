class UsersController < ApplicationController
  def new
    @user = User.new
  end

  def create
    @user = User.new(params[:user])
	@user.pokes = 0;
    if @user.save
      session[:user_id] = @user.id
      redirect_to root_url, notice: "Thank you for signing up!"
    else
      render "new"
    end
  end
  
  def index
	@users = User.all
  end
  
  def show
	@user = User.find_by_email(params[:id])
  end
  
  def poke
    @user = User.find_by_email(params[:id])
	@user.pokes = @user.pokes + 1
	@user.save
	
	redirect_to @user
  end
end
