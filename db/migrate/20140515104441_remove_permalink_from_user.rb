class RemovePermalinkFromUser < ActiveRecord::Migration
  def up
    remove_column :users, :permalink
  end

  def down
    add_column :users, :permalink, :string
  end
end
