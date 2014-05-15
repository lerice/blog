class AddPokesToUsers < ActiveRecord::Migration
  def change
    add_column :users, :pokes, :int
  end
end
